package com.example.ivanandreev.newsaggregator.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ivanandreev.newsaggregator.R
import com.example.ivanandreev.newsaggregator.adapters.KeywordAdapter
import com.example.ivanandreev.newsaggregator.firebase.FireDB
import com.example.ivanandreev.newsaggregator.firebase.UserKeywords
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
        loadedView.findViewById<FloatingActionButton>(R.id.addKeyword)
            .setOnClickListener(this::showAddKeywordDialog)
    }

    private fun showAddKeywordDialog(view: View) {
        val textBox = EditText(loadedView.context)
        val dialog: AlertDialog = AlertDialog.Builder(loadedView.context)
            .setTitle("Add Keyword")
            .setView(textBox)
            .setPositiveButton("Add") { _: DialogInterface, _: Int ->
                val keyword: String = textBox.text.toString()
                addKeyword(keyword)
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()

    }

    private fun addKeyword(keyword: String) {
        val rv: RecyclerView = loadedView.findViewById<RecyclerView>(R.id.keywords_recyclerview)
        val adapter: KeywordAdapter = rv.adapter as KeywordAdapter


        adapter.keywordList.add(keyword)
        adapter.notifyItemInserted(adapter.itemCount - 1)
    }

    private fun loadData() {
        val keywords = ArrayList<String>()

        if (userEmail != null) {
            db.getData(userEmail) { doc: DocumentSnapshot? ->
                if (doc != null && doc.data != null) {
                    for (keyword: String in doc.data!![UserKeywords::keywords.name] as ArrayList<String>) {
                        keywords.add(keyword)
                    }
                    println("!!! Successfully loaded Keywords from DB")
                } else {
                    println("!!! Doc data is empty")
                }

                loadRecyclerView(keywords)
            }
        }

    }

    private fun loadRecyclerView(keywordsList: ArrayList<String>) {
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
            val recyclerViewAdapter: KeywordAdapter =
                loadedView.findViewById<RecyclerView>(R.id.keywords_recyclerview).adapter as KeywordAdapter

            // need to wrap in POJO before saving to DB
            db.save(userEmail, UserKeywords(recyclerViewAdapter.keywordList))

            println("!!! Keywords saved")
        }
    }
}
