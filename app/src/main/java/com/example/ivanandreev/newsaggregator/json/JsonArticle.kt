package com.example.ivanandreev.newsaggregator.json

import com.example.ivanandreev.newsaggregator.fragments.NewsEntry
import com.example.ivanandreev.newsaggregator.helpers.DateConverter
import org.json.JSONObject
import java.util.*

class JsonArticle(json: String) : JSONObject(json) {
    val publisher: String = this.getJSONObject("source").getString("name")
    val author: String = this.getString("author")
    val title: String = this.getString("title")
    val url: String = this.getString("url")
    val urlToImage: String = this.getString("urlToImage")
    val publishedAt: Calendar = DateConverter.fromIsoString(this.getString("publishedAt"))
    val description: String = this.getString("description")

    fun toNewsEntry(): NewsEntry {
        return NewsEntry(title, publisher, url, urlToImage, description, publishedAt)
    }
}