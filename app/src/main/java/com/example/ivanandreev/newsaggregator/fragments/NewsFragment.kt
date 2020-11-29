package com.example.ivanandreev.newsaggregator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ivanandreev.newsaggregator.R
import com.example.ivanandreev.newsaggregator.adapters.NewsArticleAdapter
import com.example.ivanandreev.newsaggregator.helpers.RWFile
import com.example.ivanandreev.newsaggregator.json.JsonArticle
import com.example.ivanandreev.newsaggregator.json.JsonNews
import kotlin.collections.ArrayList

class NewsFragment : Fragment() {
    private val maxArticlesShown: Int = 10

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(
        R.layout.news_screen,
        container,
        false
    )!!


    override fun onResume() {
        super.onResume()
        loadRecyclerView()
    }

    override fun onStop() {
        super.onStop()
        // Trigger adapter detach callback
        view!!.findViewById<RecyclerView>(R.id.news_recyclerview)!!.adapter = null
    }

    private fun loadRecyclerView() {
        val articlesList: ArrayList<NewsEntry> = populateData()
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.news_recyclerview)
        val layoutManager = LinearLayoutManager(context)
        val recyclerAdapter = NewsArticleAdapter(articlesList, context!!)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerAdapter
    }

    private fun populateData(): ArrayList<NewsEntry> {
        val jsonNewsString: String = RWFile.readFromFile(getString(R.string.news_file), context!!)
        val news = JsonNews(jsonNewsString)

        val articles = ArrayList<NewsEntry>()
        for (i in 0..maxArticlesShown) {
            val entry: JsonArticle = news.articles[i]
            articles.add(
                NewsEntry(
                    entry.title,
                    entry.publisher,
                    entry.url,
                    entry.urlToImage,
                    entry.publishedAt
                )
            )
        }

        return articles
    }
}