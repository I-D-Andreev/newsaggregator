package com.example.ivanandreev.newsaggregator

import android.content.Context
import android.content.Intent

import com.google.android.material.bottomnavigation.BottomNavigationView

class NavBar {
    companion object{
        fun setUpBar(bar: BottomNavigationView, context: Context){
            bar.setOnNavigationItemSelectedListener { item ->
                when(item.itemId){
                    R.id.news -> changeScreen(context, News::class.java)
                    R.id.saved_articles -> changeScreen(context, SavedArticles::class.java)
                    R.id.preferences -> changeScreen(context, AccountPreferences::class.java)
                    R.id.search -> changeScreen(context, Search::class.java)
                }
                true
            }
        }

        private fun changeScreen(context: Context, cls: Class<*>){
            val intent = Intent(context, cls)
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}