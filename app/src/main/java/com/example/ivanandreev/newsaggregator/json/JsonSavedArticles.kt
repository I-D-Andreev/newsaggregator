package com.example.ivanandreev.newsaggregator.json

import com.example.ivanandreev.newsaggregator.fragments.NewsEntry
import org.json.JSONArray
import org.json.JSONObject

class JsonSavedArticles(json: String) : JSONArray(json) {
    var articles: MutableList<NewsEntry> = this
        .let { 0.until(it.length()).map { i -> it.getJSONObject(i) } }
        .map { NewsEntry.fromJson(it.toString()) }.toMutableList()

    constructor() : this("[]")
    constructor(articles_: MutableList<NewsEntry>) : this("[]") {
        articles = articles_
    }

    fun addArticle(entry: NewsEntry) {
        articles.add(entry)
    }

    fun contains(entry: NewsEntry): Boolean {
        return articles.contains(entry)
    }

    fun removeArticleAt(position: Int) {
        articles.removeAt(position)
    }

    fun toJsonArrayString(): String {
        val array: JSONArray = JSONArray()
        articles.forEach { entry ->
            // deals with additional \" symbols
            val obj = JSONObject(entry.toJson())
            array.put(obj)
        }

        return array.toString()
    }
}