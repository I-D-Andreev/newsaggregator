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
import java.util.*
import kotlin.collections.ArrayList

class SavedArticlesFragment : Fragment() {

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

        loadRecyclerView(view)
    }

    private fun loadRecyclerView(view: View) {
        val articlesList: ArrayList<SavedArticleEntry> = populateDummyData()
        val recyclerView = view.findViewById<RecyclerView>(R.id.saved_articles_recyclerview)
        val layoutManager = LinearLayoutManager(view.context)
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