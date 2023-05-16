package com.example.myclubapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import com.example.admin.AdminNavigation
import com.example.common.data.User
import com.example.common.persistance.FirebaseUtil.checkUserStatus
import com.example.common.persistance.SharedPreference
import com.example.member.MemberNavigation
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
        setContent {
            navigation()
        }
    }

}


@Composable
fun navigation() {
    loggedInUser?.let { user ->
        if (checkUserStatus() && user.isAdmin) {
            AdminNavigation()
        } else if (checkUserStatus()) {
            MemberNavigation()
        } else {
            RootNavigationHostController()
        }
    }
}