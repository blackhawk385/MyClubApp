package com.example.login.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.example.common.data.User
import com.example.common.data.AppState
import com.example.common.persistance.FirebaseUtil
import com.example.common.persistance.SharedPreference
import com.example.login.repository.ILoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(val repository: ILoginRepository) : ViewModel() {

    private val mutableState: MutableStateFlow<AppState<User>> = MutableStateFlow<AppState<User>>(
        AppState.Idle())
    val state: StateFlow<AppState<User>> = mutableState.asStateFlow()

    fun onLoginPressed(email: String, password: String){
        viewModelScope.launch {
            repository.authWithEmailAndPassword(email, password).collectLatest {
                mutableState.value = it
            }
        }
    }

    fun getUserFromFirebase(uuid: String, context: Context) {
        FirebaseUtil.getSingleDocument("user", uuid) { firebaseUser ->
            viewModelScope.launch {
                SharedPreference(context).setUser(FirebaseUtil.createUserData(firebaseUser))
            }
        }
    }

}

