package com.example.ivanandreev.newsaggregator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.ivanandreev.newsaggregator.fragments.AccountPreferencesFragment
import com.example.ivanandreev.newsaggregator.fragments.NewsFragment
import com.example.ivanandreev.newsaggregator.fragments.SavedArticlesFragment
import com.example.ivanandreev.newsaggregator.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setBottomNavBar()
    }

    private fun setBottomNavBar() {
        val navBar = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView

        navBar.setOnNavigationItemSelectedListener { item ->
            val frag: Fragment = when (item.itemId) {
                R.id.news -> NewsFragment()
                R.id.saved_articles -> SavedArticlesFragment()
                R.id.preferences -> AccountPreferencesFragment()
                R.id.search -> SearchFragment()
                else -> NewsFragment()
            }

            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, frag)
            transaction.commit()
            true
        }

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, NewsFragment())
        transaction.commit()
    }

}