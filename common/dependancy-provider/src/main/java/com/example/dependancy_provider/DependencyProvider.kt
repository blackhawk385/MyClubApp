package com.example.core.dependencyprovider

import com.example.admin_feature_api.AdminFeatureAPI
import com.example.login_feature_api.LoginFeatureAPI
import com.example.member_feature_api.MemberFeatureAPI


/**
 * WARNING!!! Don't use it in real project! Use real DI libraries: Dagger, Hilt, Koin..
 * We did this to simplify the example
 */
object DependencyProvider {

    private lateinit var loginFeatureAPI: LoginFeatureAPI
    private lateinit var memberFeatureApi: MemberFeatureAPI
    private lateinit var adminFeatureApi: AdminFeatureAPI

    fun provideImpl(
        loginFeatureApi: LoginFeatureAPI,
        memberFeatureApi: MemberFeatureAPI,
        adminFeatureApi: AdminFeatureAPI
    ) {
        this.loginFeatureAPI = loginFeatureApi
        this.memberFeatureApi = memberFeatureApi
        this.adminFeatureApi = adminFeatureApi
    }


    fun loginFeature(): LoginFeatureAPI = loginFeatureAPI

    fun memberFeature(): MemberFeatureAPI = memberFeatureApi
    fun adminFeature(): AdminFeatureAPI = adminFeatureApi
}