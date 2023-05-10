package com.example.common.persistance

import android.util.Log
import com.example.common.Club
import com.example.common.Posts
import com.example.common.data.User
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

object FirebaseUtil {

    //add and retrive data
    fun <T : Any> addData(collection: String, data: T): Boolean {
        return FirebaseFirestore.getInstance().collection(collection)
            .add(
                data
            ).isSuccessful
    }

    //for single user object - login
//    fun getUserData(collection: String, uuid: String, onSuccess: (User) -> Unit) {
//
//        FirebaseFirestore.getInstance().collection(collection).whereEqualTo("uuid", uuid).get()
//            .addOnSuccessListener {
//                if (it.documents.size > 0) {
//                    it.documents[0].let { document ->
//                        onSuccess(
//                            User(
//                                uuid = document["uuid"].toString(),
//                                fullName = document["fullName"].toString(),
//                                gender = document["gender"].toString(),
//                                email = document["email"].toString(),
//                                dob = document["dob"].toString(),
//                                city = document["city"].toString(),
//                                isAdmin = document["admin"] as Boolean
//                            )
//                        )
//                    }
//                }
//            }
//    }

//    fun getAllClubData(collection: String, onSuccess: (MutableList<Club>) -> Unit){
//        val list = mutableListOf<Club>()
//        FirebaseFirestore.getInstance().collection(collection).get()
//            .addOnSuccessListener { result ->
//                result.documents.toList()
//                for (document in result) {
//                    list.add(Club(name = document.data["name"].toString(), ))
//                    Log.d("FireBAseUtil", "${document.id} => ${document.data}")
//                }
//                onSuccess(list)
//            }
//            .addOnFailureListener { exception ->
//                Log.d("FireBAseUtil", "Error getting documents: ", exception)
//            }
//    }

    //add and get post
    fun getSingleDocument(
        collection: String,
        uuid: String,
        onSuccess: (User) -> Unit
    ) {
        FirebaseFirestore.getInstance().collection(collection).whereEqualTo("uuid", uuid).limit(1)
            .get()
            .addOnSuccessListener {
                it.documents.get(0).data?.let { it1 -> onSuccess(createUserData(it1)) }
            }
    }

    fun getAllData(
        collection: String,
        onSuccess: (List<DocumentSnapshot>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        FirebaseFirestore.getInstance().collection(collection).get()
            .addOnSuccessListener { result ->
                onSuccess(result.documents)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
                Log.d("FireBAseUtil", "Error getting documents: ", exception)
            }
    }

    fun createClubData(list: List<DocumentSnapshot>): List<Club> {
        val clubs = mutableListOf<Club>()
        list.forEach {
            clubs.add(it.toObject(Club::class.java)!!)
        }
        return clubs
    }

    fun createPostData(list: List<DocumentSnapshot>): List<Posts> {
        val posts = mutableListOf<Posts>()
        list.forEach {
            posts.add(it.toObject(Posts::class.java)!!)
        }
        return posts
    }

    fun createUserData(map: MutableMap<String, Any>) = User(
        uuid = map["uuid"].toString(),
        fullName = map["full_name"].toString(),
        dob = map["dob"].toString(),
        city = map["city"].toString(),
        isAdmin = map["admin"] as Boolean,
        gender = map["gender"].toString(),
        email = map["email"].toString()
    )

    fun updateUserDetail() {
//        FirebaseFirestore.getInstance().collection("users").document(user.id).update(
//            "firstname", "John",
//            "lastname", "Smith",
//            "age", 25
//        )
    }


    //add and get club
}