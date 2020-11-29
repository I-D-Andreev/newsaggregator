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


    override fun onResume() {
        super.onResume()
        loadRecyclerView()
        println("!!! Fragment onResume")
    }

    override fun onStop() {
        super.onStop()
        // Trigger adapter detach callback
        view!!.findViewById<RecyclerView>(R.id.news_recyclerview)!!.adapter = null
        println("!!! Fragment onStop")
    }

    private fun loadRecyclerView() {
        println("!!! Load Recycler View")
        val articlesList: ArrayList<NewsEntry> = populateDummyData()
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
        for (i in 0..10) {
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

    // for testing purposes only, so as not to use up API calls
    private fun populateDummyData(): ArrayList<NewsEntry> {
        val publishers: Array<String> = resources.getStringArray(R.array.publisher_list)
        val image = "https://ichef.bbci.co.uk/images/ic/400xn/p08yffwk.jpg"
        val articles = ArrayList<NewsEntry>()
        for (i in 0..10) {
            val title = "Article $i Title"
            val publisher = publishers[i%publishers.size]
            val date = Calendar.getInstance()

            articles.add(NewsEntry(title, publisher, "", image, date))
        }

        return articles
    }
}