package com.example.member_feature_api

import com.example.core.feature_api.FeatureApi

interface MemberFeatureAPI : FeatureApi {

    val memberRoute: String
}