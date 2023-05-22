package com.example.myclubapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.admin_feature_impl.AdminFeatureImpl
import com.example.common.data.User
import com.example.common.persistance.FirebaseUtil.checkUserStatus
import com.example.common.persistance.SharedPreference
import com.example.core.dependencyprovider.DependencyProvider
import com.example.jetcomposenavmultimodule.main.navigation.AppNavGraph
import com.example.login_feature_api.LoginFeatureAPI
import com.example.login_feature_impl.LoginFeatureImpl
import com.example.member.MemberNavigation
import com.example.member_feature_impl.MemberFeatureImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

var loggedInUser: User? = null

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            SharedPreference(applicationContext).getUser {
                loggedInUser = it
            }
        }
        DependencyProvider.provideImpl(
           loginFeatureApi = LoginFeatureImpl(),
            adminFeatureApi = AdminFeatureImpl(),
            memberFeatureApi = MemberFeatureImpl()
        )

        setContent {
            val navController = rememberNavController()

            loggedInUser?.let {
                AppNavGraph(
                    navController = navController,
                    modifier = Modifier,
                    loggedinUser = it
                )
            }
        }
    }

}


@Composable
fun navigation(navController: NavController) {
    loggedInUser?.let { user ->
        if (checkUserStatus() && user.isAdmin) {
            val admin = DependencyProvider.adminFeature()
            navController.navigate(admin.adminRoute) {
                popUpTo("auth-route") { inclusive = true }
            }
        } else if (checkUserStatus()) {
            val member = DependencyProvider.memberFeature()
            navController.navigate(member.memberRoute) {
                popUpTo("auth-route") { inclusive = true }
            }
        } else {
            val login = DependencyProvider.loginFeature()
            navController.navigate(login.loginRoute) {
                popUpTo("auth-route") { inclusive = true }
            }
        }
    }
}