package com.example.ivanandreev.newsaggregator.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.ivanandreev.newsaggregator.R
import com.example.ivanandreev.newsaggregator.firebase.UserTopics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AccountTopicsFragment : Fragment() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val userEmail: String? = FirebaseAuth.getInstance().currentUser?.email
    private val TAG: String = AccountTopicsFragment::class.java.simpleName

    lateinit var userTopics: UserTopics
    lateinit var loadedView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.account_topics_fragment, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadedView = view
        loadGridView()
    }


    private fun loadGridView() {
        val grid = loadedView.findViewById<GridLayout>(R.id.news_type_grid)
        userTopics = UserTopics(loadedView.context)

        for (i in 0 until grid.childCount) {
            (grid.getChildAt(i) as TextView).text = userTopics.topicsList[i]
        }

        loadDB()
    }

    private fun loadDB() {
        if (userEmail != null) {
            db.collection("userTopics").document(userEmail).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        println("!!! DocumentSnapshot data: ${document.data}")
                    } else {
                        println("!!! No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    println("!! Get failed with ${exception.message}")
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        println("!!! Topics Destroyed")
        if (userEmail != null) {
            db.collection("userTopics").document(userEmail).set(userTopics.topics)
                .addOnSuccessListener {
                    Log.i(TAG, "Successfully written to DB!")
                }.addOnFailureListener { ex ->
                    Log.e(TAG, "Failure writing to DB - ${ex.message}")
                }
        }
    }

}
