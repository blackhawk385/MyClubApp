package com.example.common.persistance

import android.util.Log
import com.example.common.*
import com.example.common.data.AppState
import com.example.common.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlin.collections.HashMap

const val REQUEST_COLLECTION = "requests"
const val USER_COLLECTION = "user"
const val POST_COLLECTION = "posts"
const val CLUB_COLLECTION = "clubs"
const val COMMENTS_COLLECTION = "comments"

object FirebaseUtil {

    //add and retrive data
    fun <T : Any> addData(collection: String, data: T): Boolean {
        return FirebaseFirestore.getInstance().collection(collection).add(
            data
        ).isSuccessful
    }

    //add and get post
    fun getSingleDocument(
        collection: String, uuid: String, onSuccess: (MutableMap<String, Any>) -> Unit
    ) {
        FirebaseFirestore.getInstance().collection(collection).whereEqualTo("uuid", uuid).limit(1)
            .get().addOnSuccessListener {
                it.documents.get(0).data?.let { it1 -> onSuccess(it1) }
            }
    }

    fun getAllDataFromACollection(
        collection: String,
        onSuccess: (List<DocumentSnapshot>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        FirebaseFirestore.getInstance().collection(collection).get()
            .addOnSuccessListener { result ->
                onSuccess(result.documents)
            }.addOnFailureListener { exception ->
                onFailure(exception)
                Log.d("FireBAseUtil", "Error getting documents: ", exception)
            }
    }

    fun getClubRelatedUserData() {

        //get club objects where
//        FirebaseFirestore.getInstance().collection()
    }

    fun getConditionalListOfDocumentSnapShot(
        collection: String,
        condition: String,
        uuid: String,
        onSuccess: (List<DocumentSnapshot>) -> Unit
    ) {
        //get all post document from post collection where author uuid is equal to provided
        FirebaseFirestore.getInstance().collection(collection).whereEqualTo(condition, uuid).get()
            .addOnSuccessListener {
                onSuccess(it.documents)
            }
    }


    fun createClubData(list: List<DocumentSnapshot>): List<Club> {
        val clubs = mutableListOf<Club>()
        list.forEach {
            clubs.add(it.toObject(Club::class.java)!!)
        }
        return clubs
    }

    fun createCommentData(list: List<DocumentSnapshot>): List<Comments> {
        val clubs = mutableListOf<Comments>()
        list.forEach {
            clubs.add(it.toObject(Comments::class.java)!!)
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

    fun createListOfRequestData(list: List<DocumentSnapshot>): List<Request> {
        val request = mutableListOf<Request>()
        list.forEach {
            request.add(it.toObject(Request::class.java)!!)
        }
        return request
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

    fun createClubData(map: MutableMap<String, Any>) = Club(
        uuid = map["uuid"].toString(),
        name = map["name"].toString(),
        location = map["location"].toString(),
        createdBy = if (map["createdBy"] == null) User() else createUserData(map["createdBy"] as HashMap<String, Any>)
    )

    fun createPostData(map: MutableMap<String, Any>) = Posts(
        title = map["title"].toString(),
        description = map["description"].toString(),
        associateClub = if (map["associateClub"] == null) Club() else createClubData(map["associateClub"] as HashMap<String, Any>),
        link = map["link"].toString(),
        author = if (map["createdBy"] == null) User() else createUserData(map["createdBy"] as HashMap<String, Any>)
    )


    suspend fun loginFirebaseUser(email: String, password: String) =
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .await().user?.let { createUser(it) }

    fun createUser(user: FirebaseUser) = User(uuid = user.uid, email = user.email!!)

    suspend fun registerFirebaseUser(email: String, password: String) =
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .await().user?.let {
                createUser(it)
            }

    fun logoutFirebaseUser() {
        Firebase.auth.signOut()
    }

    fun checkUserStatus(): Boolean {
        return Firebase.auth.currentUser != null
    }


    fun updateClubDetail(collection: String, data: Club, onSuccess: () -> Unit) {
        FirebaseFirestore.getInstance().collection(collection)
            .whereEqualTo("uuid", data.uuid)
            .get().addOnSuccessListener {
                it.forEach {
                    it.reference.set(data, SetOptions.merge())
                }
                onSuccess()
            }
    }

    fun updateUserDetail(collection: String, data: User, onSuccess: () -> Unit) {
        FirebaseFirestore.getInstance().collection(collection)
            .whereEqualTo("uuid", data.uuid)
            .get().addOnSuccessListener {
                it.forEach {
                    it.reference.set(data, SetOptions.merge())
                }
                onSuccess()
            }
    }


    fun updateClubWithPost(
        clubUUID: String,
        post: Posts,
        onSuccess: (Boolean) -> Unit,
    ) {
//        FirebaseFirestore.getInstance().collection("clubs").document(clubUUID).update(
//            FieldValue.arrayUnion(post)
//        ).addOnSuccessListener {
//            onSuccess(true)
//        }.addOnFailureListener {
//            onSuccess(false)
//        }
    }


//add and get club
}