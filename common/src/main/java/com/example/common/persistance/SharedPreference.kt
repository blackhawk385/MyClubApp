package com.example.common.persistance

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.codelab.android.datastore.UserPreferences
import com.example.common.UserPreferencesSerializer
import com.example.common.data.User
import com.example.common.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import java.io.IOException


private val USER_FULL_NAME = stringPreferencesKey("user_full_name")
private val USER_UUID = stringPreferencesKey("user_uuid")
private val USER_DOB = longPreferencesKey("user_dob")
private val USER_CITY = longPreferencesKey("user_city")
private val USER_IS_ADMIN = longPreferencesKey("user_is_admin")
private val USER_GENDER = longPreferencesKey("user_gender")
private val USER_EMAIL = longPreferencesKey("user_email")

class SharedPreference(private val context: Context) {
    suspend fun setUser(user: User) {
        context.dataStore.updateData {
            it.copy(
                fullName = user.fullName,
                dob = user.dob,
                city = user.city,
                email = user.email,
                isAdmin = user.isAdmin,
                gender = user.gender,
                uuid = user.uuid
            )
        }
    }

    suspend fun getUser(onDataLoad: (User) -> Unit){
        context.dataStore.data.collectLatest {
            onDataLoad(it)
        }
    }

//    suspend fun updateShowCompleted(completed: Boolean) {
//        userPreferencesStore.updateData { preferences ->
//            preferences.toBuilder().setShowCompleted(completed).build()
//        }
//    }
//
//    private val TAG: String = "UserPreferencesRepo"
//
//    val userPreferencesFlow: Flow<UserPreferences> = userPreferencesStore.data
//        .catch { exception ->
//            // dataStore.data throws an IOException when an error is encountered when reading data
//            if (exception is IOException) {
//                Log.e(TAG, "Error reading sort order preferences.", exception)
//                emit(UserPreferences.getDefaultInstance())
//            } else {
//                throw exception
//            }
//        }


//    fun saveUserToPreferencesStore(user: User) {
//        userPreferencesDataStore.edit { preferences ->
//            preferences[USER_FULL_NAME] = user.fullName
//            preferences[USER_UUID] = user.uuid
//            preferences[USER_CITY] = user.city
//            preferences[USER_DOB] = user.dob
//            preferences[USER_IS_ADMIN] = user.isAdmin
//            preferences[USER_GENDER] = user.gender
//            preferences[USER_EMAIL] = user.email
//        }
//    }
}