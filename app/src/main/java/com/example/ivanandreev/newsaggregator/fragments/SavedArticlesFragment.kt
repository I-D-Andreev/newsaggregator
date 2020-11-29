package com.example.ivanandreev.newsaggregator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateData()
        loadRecyclerView(view)
    }

    private fun loadRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.saved_articles_recyclerview)
        val layoutManager = LinearLayoutManager(view.context)
        val recyclerAdapter = SavedArticleAdapter(currentSavedArticles.articles)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerAdapter
    }

    private fun populateData(){
        val fileName: String = getString(R.string.saved_articles_file)
        val currentData: String = RWFile.readFromFile(fileName, context!!)

        if (currentData.isNotEmpty()){
            currentSavedArticles = JsonSavedArticles(currentData)
        }
    }
}