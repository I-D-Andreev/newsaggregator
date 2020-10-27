package com.example.ivanandreev.newsaggregator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ivanandreev.newsaggregator.adapters.KeywordAdapter

class AccountPreferences : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_preferences)

        loadGridView()
        loadRecyclerView()
    }

    private fun loadGridView() {
        val grid = findViewById<GridLayout>(R.id.news_type_grid)
        val newsTypes: Array<String> = resources.getStringArray(R.array.news_types_list)

        for (i in 0 until grid.childCount) {
            (grid.getChildAt(i) as TextView).text = newsTypes[i]
        }
    }

    private fun loadRecyclerView() {
        val keywordsList: ArrayList<KeywordEntry> = populateDummyData()
        val recyclerView = findViewById<RecyclerView>(R.id.keywords_recyclerview)
        val layoutManager = GridLayoutManager(this, 2)
        val recyclerAdapter = KeywordAdapter(keywordsList)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerAdapter
    }

    private fun populateDummyData(): ArrayList<KeywordEntry> {
        val keywords = ArrayList<KeywordEntry>()
        val stringList: Array<String> = resources.getStringArray(R.array.keywords_list)
        for (elem in stringList) {
            val temp = KeywordEntry()
            temp.keyword = elem
            keywords.add(temp)
        }
        return keywords
    }
}