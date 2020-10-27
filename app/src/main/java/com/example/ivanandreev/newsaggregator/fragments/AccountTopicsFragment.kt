package com.example.ivanandreev.newsaggregator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.ivanandreev.newsaggregator.R

class AccountTopicsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.account_topics_fragment, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadGridView(view)
    }


    private fun loadGridView(view:View) {
        val grid = view.findViewById<GridLayout>(R.id.news_type_grid)
        val newsTypes: Array<String> = resources.getStringArray(R.array.news_types_list)

        for (i in 0 until grid.childCount) {
            (grid.getChildAt(i) as TextView).text = newsTypes[i]
        }
    }
}
