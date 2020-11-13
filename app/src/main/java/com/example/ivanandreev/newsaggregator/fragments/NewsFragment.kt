package com.example.ivanandreev.newsaggregator.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ivanandreev.newsaggregator.R
import com.example.ivanandreev.newsaggregator.adapters.NewsArticleAdapter
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayList

class NewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(
        R.layout.news_screen,
        container,
        false
    )!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadRecyclerView(view)
    }

    private fun loadRecyclerView(view: View) {
        val articlesList: ArrayList<NewsEntry> = populateDummyData()
        val recyclerView = view.findViewById<RecyclerView>(R.id.news_recyclerview)
        val layoutManager = LinearLayoutManager(view.context)
        val recyclerAdapter = NewsArticleAdapter(articlesList)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerAdapter
    }

    private fun populateDummyData(): ArrayList<NewsEntry> {
        val publishers: Array<String> = resources.getStringArray(R.array.publisher_list)
        val image = R.drawable.human

        val articles = ArrayList<NewsEntry>()
        for (i in 1..10) {
            val title = "Article $i Title"
            val publisher = publishers[(0..4).random()]
            val date = Calendar.getInstance()

            articles.add(NewsEntry(title, publisher, image, date))
        }

        return articles
    }

    private fun readNewsFile(): String {
        val fileIn = context?.openFileInput(getString(R.string.news_file))
        val fileReader = InputStreamReader(fileIn)
        return fileReader.readText().substring(0,10);
    }
}