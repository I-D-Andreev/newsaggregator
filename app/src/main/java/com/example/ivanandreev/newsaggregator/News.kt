package com.example.ivanandreev.newsaggregator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class News : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_screen)

        setUpNavBar()
       }

    private fun setUpNavBar() {
        val navBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navBar.menu.getItem(0).isChecked = true
        NavBar.setUpBar(navBar, this)
    }
}