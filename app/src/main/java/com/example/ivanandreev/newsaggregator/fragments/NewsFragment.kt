package com.example.ivanandreev.newsaggregator.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ivanandreev.newsaggregator.R
import com.example.ivanandreev.newsaggregator.adapters.NewsArticleAdapter
import com.example.ivanandreev.newsaggregator.firebase.FireDB
import com.example.ivanandreev.newsaggregator.firebase.UserKeywords
import com.example.ivanandreev.newsaggregator.helpers.ArticlesFilter
import com.example.ivanandreev.newsaggregator.helpers.DateConverter
import com.example.ivanandreev.newsaggregator.helpers.NewestArticle
import com.example.ivanandreev.newsaggregator.helpers.RWFile
import com.example.ivanandreev.newsaggregator.json.JsonNews
import com.google.firebase.firestore.DocumentSnapshot

class NewsFragment : Fragment() {
    private val db: FireDB = FireDB(FireDB.USER_KEYWORDS)
    private val userEmail: String? =
        com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.email
    private val logTag = NewsFragment::class.java.simpleName


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
                NewestArticle.updateNewestArticle(articlesList, context!!)
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
        val maxArticlesShown = resources.getInteger(R.integer.max_articles_shown)
        val jsonNewsString: String = RWFile.readFromFile(getString(R.string.news_file), context!!)
        val news = JsonNews(jsonNewsString)
        return ArticlesFilter.filterArticles(news, keywords, maxArticlesShown)
    }
}