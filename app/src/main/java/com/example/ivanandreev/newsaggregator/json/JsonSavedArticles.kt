package com.example.ivanandreev.newsaggregator.json

import com.example.ivanandreev.newsaggregator.fragments.NewsEntry
import org.json.JSONArray
import org.json.JSONObject

class JsonSavedArticles(json: String) : JSONObject(json) {
    val articles = this.getJSONArray("articles")
        .let { 0.until(it.length()).map {i -> it.getJSONObject(i) }}
        .map {NewsEntry.fromJson(it.toString())}.toMutableList()

    fun addArticle(entry: NewsEntry){
        articles.add(entry)
    }

    fun removeArticleAt(position: Int){
        articles.removeAt(position)
    }

    fun toJsonArray(): String{
        val array: JSONArray = JSONArray()
        articles.forEach { entry->
            array.put(entry.toJson())
        }

        println("!!! JSON array is ${array.toString()}")
        return array.toString()
    }
}