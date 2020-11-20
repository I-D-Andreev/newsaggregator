package com.example.ivanandreev.newsaggregator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ivanandreev.newsaggregator.R
import com.example.ivanandreev.newsaggregator.adapters.KeywordAdapter
import com.example.ivanandreev.newsaggregator.firebase.FireDB
import com.example.ivanandreev.newsaggregator.firebase.UserKeywords
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot

class AccountKeywordsFragment : Fragment() {
    private val userEmail: String? = FirebaseAuth.getInstance().currentUser?.email
    private val db: FireDB = FireDB("userKeywords")
    private lateinit var loadedView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.account_keywords_fragment, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadedView = view
        loadData()
    }

    private fun loadData() {
        val keywords = ArrayList<KeywordEntry>()

        if (userEmail != null) {
            db.getData(userEmail) { doc: DocumentSnapshot? ->
                if (doc != null && doc.data != null) {
                    for (keyword: String in doc.data!![UserKeywords::keywords.name] as ArrayList<String>) {
                        val entry = KeywordEntry()
                        entry.keyword = keyword
                        keywords.add(entry)
                    }
                    println("!!! Successfully loaded Keywords from DB")
                } else {
                    println("!!! Doc data is empty")
                }

                loadRecyclerView(keywords)
            }
        }

    }

    private fun loadRecyclerView(keywordsList: ArrayList<KeywordEntry>) {
        val recyclerView = loadedView.findViewById<RecyclerView>(R.id.keywords_recyclerview)
        val layoutManager = GridLayoutManager(loadedView.context, 2)
        val recyclerAdapter = KeywordAdapter(keywordsList)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerAdapter
    }

    override fun onStop() {
        super.onStop()
        println("!!! Keywords Stopped")

        if (userEmail != null) {
            val recyclerView = loadedView.findViewById<RecyclerView>(R.id.keywords_recyclerview)
            val keywords = recyclerView.children.map {
                it.findViewById<MaterialTextView>(R.id.keyword).text.toString()
            }.toMutableList()

            // need to wrap in POJO before saving to DB
            db.save(userEmail, UserKeywords(keywords))

            println("!!! Keywords saved")
        }
    }
}
