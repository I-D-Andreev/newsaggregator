package com.example.ivanandreev.newsaggregator.json

import org.json.JSONObject
import java.util.*
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class JsonArticle(json: String) : JSONObject(json) {
    val publisher: String = this.getJSONObject("source").getString("name")
    val author: String = this.getString("author")
    val title: String = this.getString("title")
    val url: String = this.getString("url")
    val urlToImage: String = this.getString("urlToImage")
    val publishedAt: Calendar = GregorianCalendar.from(
        ZonedDateTime.ofInstant(
            Instant.parse(this.getString("publishedAt")),
            ZoneId.systemDefault()
        )
    )
    val description: String = this.getString("description")
}