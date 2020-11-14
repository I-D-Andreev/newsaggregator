package com.example.ivanandreev.newsaggregator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ivanandreev.newsaggregator.R
import com.example.ivanandreev.newsaggregator.adapters.KeywordAdapter

class AccountKeywordsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.account_keywords_fragment, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadRecyclerView(view)
    }

    private fun loadRecyclerView(view: View) {
        val keywordsList: ArrayList<KeywordEntry> = populateDummyData()
        val recyclerView = view.findViewById<RecyclerView>(R.id.keywords_recyclerview)
        val layoutManager = GridLayoutManager(view.context, 2)
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


    override fun onStop() {
        super.onStop()
        println("!!! Keywords Stopped")
    }
}
