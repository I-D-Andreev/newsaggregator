package com.example.ivanandreev.newsaggregator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class Search : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_screen)

        setUpNavBar()
    }

    private fun setUpNavBar() {
        val navBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        NavBar.setUpBar(navBar, this)
    }
}