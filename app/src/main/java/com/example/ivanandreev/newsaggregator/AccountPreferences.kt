package com.example.ivanandreev.newsaggregator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import android.widget.TextView

class AccountPreferences : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_preferences)

        loadGridView()
    }

    private fun loadGridView(){
        val grid = findViewById<GridLayout>(R.id.news_type_grid)
        val newsTypes: Array<String> = resources.getStringArray(R.array.news_types_list)

        for(i in 0 until grid.childCount){
            (grid.getChildAt(i) as TextView).text = newsTypes[i]
        }
    }
}