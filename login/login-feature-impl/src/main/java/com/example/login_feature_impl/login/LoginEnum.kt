package com.example.login

enum class LoginEnum {

    LoginScreen,
    RegisterScreen;

    companion object {
        fun fromRoute(route: String?) : LoginEnum =
            when(route?.substringBefore("/")) {
                LoginScreen.name -> LoginScreen
                RegisterScreen.name -> RegisterScreen
                null -> LoginScreen
                else -> throw java.lang.IllegalArgumentException("$route is not defined")
            }
    }
}