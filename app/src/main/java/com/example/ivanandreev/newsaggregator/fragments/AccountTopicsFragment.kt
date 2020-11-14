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
import com.example.ivanandreev.newsaggregator.firebase.FireDB
import com.example.ivanandreev.newsaggregator.firebase.UserTopics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class AccountTopicsFragment : Fragment() {
    private val userEmail: String? = FirebaseAuth.getInstance().currentUser?.email
    private val db: FireDB = FireDB("userTopics")
    private lateinit var userTopics: UserTopics
    private lateinit var loadedView: View

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
            db.getData("adsadsd") { doc: DocumentSnapshot? ->
                if (doc!=null) {
                    println("!!! Document is ${doc.data}")
                } else {
                    println("!!! doc is null")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        println("!!! Topics Destroyed")
        if (userEmail != null) {
            db.save(userEmail, userTopics.topics)
        }
    }

}
