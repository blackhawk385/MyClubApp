package com.example.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.common.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import kotlin.properties.ReadOnlyProperty

private val USER_PREFERENCES_NAME = "user_preferences"

val  Context.dataStore by dataStore("user.json", UserPreferencesSerializer)

object UserPreferencesSerializer : Serializer<User> {


    override val defaultValue: User
        get() = User()

    override suspend fun readFrom(input: InputStream): User {
        return try {
            Json.decodeFromString(
                deserializer = User.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: User, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = User.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        }
    }
}

//prefbuff implementation

//object UserPreferencesSerializer : Serializer<UserPreferences> {
//        override val defaultValue: UserPreferences = UserPreferences.getDefaultInstance()
//        override suspend fun readFrom(input: InputStream): UserPreferences {
//            try {
//                return UserPreferences.parseFrom(input)
//            } catch (exception: InvalidProtocolBufferException) {
//                throw CorruptionException("Cannot read proto.", exception)
//            }
//        }
//
//        override suspend fun writeTo(t: UserPreferences, output: OutputStream) = t.writeTo(output)
//
//    val Context.userPreferencesStore: DataStore<UserPreferences> by dataStore(
//        fileName = USER_PREFERENCES_NAME,
//        serializer = UserPreferencesSerializer
//    )
//}