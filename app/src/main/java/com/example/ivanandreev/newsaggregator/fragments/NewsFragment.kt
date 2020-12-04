package com.example.ivanandreev.newsaggregator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ivanandreev.newsaggregator.R
import com.example.ivanandreev.newsaggregator.adapters.NewsArticleAdapter
import com.example.ivanandreev.newsaggregator.firebase.FireDB
import com.example.ivanandreev.newsaggregator.firebase.UserKeywords
import com.example.ivanandreev.newsaggregator.helpers.RWFile
import com.example.ivanandreev.newsaggregator.json.JsonArticle
import com.example.ivanandreev.newsaggregator.json.JsonNews
import com.google.firebase.firestore.DocumentSnapshot
import java.util.*
import kotlin.collections.ArrayList

class NewsFragment : Fragment() {
    private val maxArticlesShown: Int = 10
    private val db: FireDB = FireDB("userKeywords")
    private val userEmail: String? =
        com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.email


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
        load()
    }

    override fun onStop() {
        super.onStop()
        // Trigger adapter detach callback
        view!!.findViewById<RecyclerView>(R.id.news_recyclerview)!!.adapter = null
    }

    private fun load() {
        val keywords = ArrayList<String>()

        if (userEmail != null) {
            db.getData(userEmail) { doc: DocumentSnapshot? ->
                if (doc != null && doc.data != null) {
                    for (keyword: String in doc.data!![UserKeywords::keywords.name] as ArrayList<String>) {
                        keywords.add(keyword)
                    }
                }

                val articlesList: ArrayList<NewsEntry> = populateData(keywords)
                loadRecyclerView(articlesList)
            }
        }
    }

    private fun loadRecyclerView(articlesList: ArrayList<NewsEntry>) {
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.news_recyclerview)
        val layoutManager = LinearLayoutManager(context)
        val recyclerAdapter = NewsArticleAdapter(articlesList, context!!)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerAdapter
    }

    // populate data from file that contains the specified keywords
    private fun populateData(keywords: ArrayList<String>): ArrayList<NewsEntry> {
        val jsonNewsString: String = RWFile.readFromFile(getString(R.string.news_file), context!!)
        val news = JsonNews(jsonNewsString)
        val articles = ArrayList<NewsEntry>()

        // firstly add articles that contain a certain keyword
        for (article: JsonArticle in news.articles) {
            for (keyword: String in keywords) {
                if (keyword.toLowerCase(Locale.getDefault()) in article.title.toLowerCase(Locale.getDefault())) {
                    articles.add(
                        NewsEntry(
                            article.title,
                            article.publisher,
                            article.url,
                            article.urlToImage,
                            article.publishedAt
                        )
                    )
                    break
                }
            }

            if (articles.size >= maxArticlesShown) {
                break
            }
        }

        // then, if we don't have enough articles, fill in the rest with whatever
        for (article: JsonArticle in news.articles) {
            if (articles.size >= maxArticlesShown) {
                break
            }

            val entry = NewsEntry(
                article.title,
                article.publisher,
                article.url,
                article.urlToImage,
                article.publishedAt
            )

            if (!articles.contains(entry)) {
                articles.add(entry)
            }
        }

        return articles
    }
}