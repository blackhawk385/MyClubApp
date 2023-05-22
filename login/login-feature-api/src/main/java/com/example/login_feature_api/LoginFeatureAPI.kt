package com.example.login_feature_api

import com.example.core.feature_api.FeatureApi

interface LoginFeatureAPI : FeatureApi{
    val loginRoute: String
}