package com.example.ivanandreev.newsaggregator.firebase

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class FireDB(private val collectionName: String) {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun addToArray(
        documentName: String,
        fieldName: String,
        data: Any,
        callback: ((task: Task<Void>) -> Unit)
    ) {
        db.collection(collectionName).document(documentName)
            .update(fieldName, FieldValue.arrayUnion(data))
            .addOnCompleteListener { task: Task<Void> ->
                callback(task)
            }
    }

    fun save(documentName: String, data: Any) {
        val logTag = "$collectionName::$documentName"
        db.collection(collectionName).document(documentName).set(data)
            .addOnSuccessListener {
                Log.i(logTag, "Successfully written to Database!")
            }
            .addOnFailureListener { exception ->
                Log.e(logTag, "Failure writing to Database - ${exception.message}")
            }
    }

    fun getData(documentName: String, callback: ((doc: DocumentSnapshot?) -> Unit)) {
        val logTag = "$collectionName::$documentName"
        db.collection(collectionName).document(documentName).get()
            .addOnSuccessListener { document: DocumentSnapshot? ->
                callback(document)
            }.addOnFailureListener { exception ->
                Log.e(logTag, "getData failed with ${exception.message}")
            }
    }

    companion object {
        // We don't want these externalised as it will mess up our database.
        const val USER_KEYWORDS = "userKeywords"
    }
}