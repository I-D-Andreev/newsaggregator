package com.example.ivanandreev.newsaggregator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ivanandreev.newsaggregator.R
import com.example.ivanandreev.newsaggregator.adapters.NewsArticleAdapter
import com.example.ivanandreev.newsaggregator.json.JsonArticle
import com.example.ivanandreev.newsaggregator.json.JsonNews
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
        val articlesList: ArrayList<NewsEntry> = populateData()
        val recyclerView = view.findViewById<RecyclerView>(R.id.news_recyclerview)
        val layoutManager = LinearLayoutManager(view.context)
        val recyclerAdapter = NewsArticleAdapter(articlesList)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerAdapter
    }

    private fun populateData(): ArrayList<NewsEntry> {
        val jsonNewsString: String = readFromFile(getString(R.string.news_file))
        val news = JsonNews(jsonNewsString)

        val articles = ArrayList<NewsEntry>()
        for (i in 0..10) {
            val entry: JsonArticle = news.articles[i]
            articles.add(NewsEntry(entry.title, entry.publisher, entry.urlToImage, entry.publishedAt))
        }

        return articles
    }

    private fun readFromFile(fileName: String): String {
        val fileIn = context?.openFileInput(fileName)
        val fileReader = InputStreamReader(fileIn)
        val json = fileReader.readText()
        fileReader.close()
        return json
    }

    // for testing purposes only, so as not to use API calls
    private fun populateDummyData(): ArrayList<NewsEntry> {
        val publishers: Array<String> = resources.getStringArray(R.array.publisher_list)
        val image = "https://ichef.bbci.co.uk/images/ic/400xn/p08yffwk.jpg"
        val articles = ArrayList<NewsEntry>()
        for (i in 0..10) {
            val title = "Article $i Title"
            val publisher = publishers[(0..4).random()]
            val date = Calendar.getInstance()

            articles.add(NewsEntry(title, publisher, image, date))
        }

        return articles
    }


}