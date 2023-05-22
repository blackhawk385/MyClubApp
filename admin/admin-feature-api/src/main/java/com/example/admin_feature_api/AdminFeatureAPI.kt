package com.example.admin_feature_api

import com.example.core.feature_api.FeatureApi

interface AdminFeatureAPI : FeatureApi{

    val adminRoute: String
}