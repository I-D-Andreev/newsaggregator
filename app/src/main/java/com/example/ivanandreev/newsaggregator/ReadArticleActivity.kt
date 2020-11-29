package com.example.ivanandreev.newsaggregator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

// Needed to load mobile version of website.
// We will only visit a pre-defined set of known websites (as we filter news sources when we fetch
// from the news API), so the security implications will not be as severe.
@SuppressLint("SetJavaScriptEnabled")
class ReadArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.read_article_screen)
        val extras = intent.extras

        if(extras != null){
            val url: String = extras.getString(resources.getString(R.string.read_article_url_field))!!
            loadArticle(url)
        }
    }
    
    fun onBackButtonClicked(view: View){
        finish()
    }

    private fun loadArticle(url: String){
        val webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)
    }
}