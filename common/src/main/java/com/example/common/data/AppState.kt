package com.example.common.data

//val data: T? = null, val message: String? = null
//class hierarchy for response
sealed class AppState<T>(val data: T? = null, val message: String? = null) {

    data class Success<T>(val response: T) : AppState<T>(response)    //generic, instance can be of any type
    data class Error<T>(val error: T) : AppState<T>(error)       //concrete class


    //singleton class (only one instance)
    class Loading<T> : AppState<T>()
    class Idle<T> : AppState<T>()

    val isFail: Boolean get() = this is Error
    val valueOrNull: Success<T>? get() = (this as? Success<T>)
}