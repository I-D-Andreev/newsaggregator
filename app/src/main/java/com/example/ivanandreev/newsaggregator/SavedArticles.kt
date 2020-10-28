package com.example.ivanandreev.newsaggregator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ivanandreev.newsaggregator.adapters.SavedArticleAdapter
import com.example.ivanandreev.newsaggregator.fragments.SavedArticleEntry
import java.util.*
import kotlin.collections.ArrayList

class SavedArticles : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.saved_articles)

        loadRecyclerView()
    }

    private fun loadRecyclerView() {
        val articlesList: ArrayList<SavedArticleEntry> = populateDummyData()
        val recyclerView = findViewById<RecyclerView>(R.id.saved_articles_recyclerview)
        val layoutManager = LinearLayoutManager(this)
        val recyclerAdapter = SavedArticleAdapter(articlesList)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerAdapter
    }

    private fun populateDummyData(): ArrayList<SavedArticleEntry> {
        val publishers: Array<String> = resources.getStringArray(R.array.publisher_list)
        val image = R.drawable.human

        val articles = ArrayList<SavedArticleEntry>()
        for (i in 1..10) {
            val title = "Article $i Title"
            val publisher = publishers[(0..4).random()]
            val date = Calendar.getInstance()

            articles.add(SavedArticleEntry(title, publisher, image, date))
        }

        return articles
    }
}