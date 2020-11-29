package com.example.ivanandreev.newsaggregator.helpers

import android.content.Context
import android.content.Intent
import com.example.ivanandreev.newsaggregator.R
import com.example.ivanandreev.newsaggregator.ReadArticleActivity

class ArticleReader {
    companion object {
        fun readArticle(url: String, context: Context){
            val intent = Intent(context, ReadArticleActivity::class.java)
            intent.putExtra(context.getString(R.string.read_article_url_field), url)
            context.startActivity(intent)
        }
    }
}