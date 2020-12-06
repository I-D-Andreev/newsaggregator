package com.example.ivanandreev.newsaggregator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ivanandreev.newsaggregator.R
import com.example.ivanandreev.newsaggregator.adapters.SavedArticleAdapter
import com.example.ivanandreev.newsaggregator.helpers.RWFile
import com.example.ivanandreev.newsaggregator.json.JsonSavedArticles

class SavedArticlesFragment : Fragment() {
    var currentSavedArticles: JsonSavedArticles = JsonSavedArticles()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(
        R.layout.saved_articles_screen,
        container,
        false
    )!!

    override fun onResume() {
        super.onResume()
        populateDataIfExists()
        loadRecyclerView()
    }

    override fun onStop() {
        super.onStop()
        // Trigger adapter detach callback
        view!!.findViewById<RecyclerView>(R.id.saved_articles_recyclerview)!!.adapter = null
    }

    private fun loadRecyclerView() {
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.saved_articles_recyclerview)
        val layoutManager = LinearLayoutManager(context)
        val recyclerAdapter = SavedArticleAdapter(currentSavedArticles.articles, context!!)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerAdapter
    }

    private fun populateDataIfExists() {
        val fileName: String = getString(R.string.saved_articles_file)
        val currentData: String = RWFile.readFromFilePersonalized(fileName, context!!)

        if (currentData.isNotEmpty()) {
            currentSavedArticles = JsonSavedArticles(currentData)
        }
    }
}