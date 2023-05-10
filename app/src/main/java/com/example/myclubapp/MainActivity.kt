package com.example.myclubapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.admin.AdminNavigation
import com.example.common.Club
import com.example.common.DashboardTabView
import com.example.common.IMemberDashboard
import com.example.common.data.User
import com.example.common.persistance.FirebaseUtil
import com.example.common.persistance.SharedPreference
import com.example.login.LoginNavigation
import com.example.login.LoginScreen
import com.example.login.RegisterScreen
import com.example.login.checkUserStatus
import com.example.login.repository.RegisterRepository
import com.example.member.MemberDashboard
import com.example.member.MemberNavigation
import com.example.myclubapp.ui.theme.MyClubAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        CoroutineScope(Dispatchers.IO).launch {
//            SharedPreference(applicationContext).getUser {
//                it
//            }
//        }



        setContent {
            MyClubAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        if (checkUserStatus()) {
                            AdminNavigation()
                        } else {
                            RootNavigationHostController()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyClubAppTheme {
        Greeting("Android")
    }
}

@Composable
fun HomeScreen() {
    Text(text = "Home")
}

@Composable
fun AboutScreen() {
    Text(text = "AboutScreen")
}

@Composable
fun SettingsScreen() {
    Text(text = "SettingsScreen")
}