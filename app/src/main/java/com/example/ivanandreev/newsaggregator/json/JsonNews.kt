package com.example.ivanandreev.newsaggregator.json

import org.json.JSONObject

class JsonNews(json: String) : JSONObject(json) {
    val status: String = this.getString("status")
    val totalResults: Int = this.getInt("totalResults")
    val articles = this.getJSONArray("articles")
        .let { 0.until(it.length()).map { i -> it.getJSONObject(i) } }
        .map { JsonArticle(it.toString()) }
}