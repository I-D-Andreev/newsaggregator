package com.example.ivanandreev.newsaggregator.fragments

import com.example.ivanandreev.newsaggregator.helpers.DateConverter
import org.json.JSONObject
import java.util.*

class NewsEntry(
    val title: String,
    val publisher: String,
    val articleUrl: String,
    val imageUrl: String,
    val summary: String,
    val date: Calendar
) {
    fun toJson(): String {
        val newsEntryJson: JSONObject = JSONObject()
        newsEntryJson.put("title", title)
        newsEntryJson.put("publisher", publisher)
        newsEntryJson.put("articleUrl", articleUrl)
        newsEntryJson.put("imageUrl", imageUrl)
        newsEntryJson.put("summary", summary)
        newsEntryJson.put("date", date.toInstant().toString())

        return newsEntryJson.toString()
    }

    override fun toString(): String {
        return "$title | $publisher | $articleUrl | $imageUrl | $date"
    }

    override fun hashCode(): Int {
        // override hashCode() based on reference as we are not doing reference comparison anymore
        return this.title.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return (other != null) && (other is NewsEntry) && this.title == other.title
                && this.publisher == other.publisher && this.articleUrl == other.articleUrl
                && this.imageUrl == other.imageUrl
    }

    companion object {
        fun fromJson(jsonString: String): NewsEntry {
            val jsonObj: JSONObject = JSONObject(jsonString)
            val title: String = jsonObj.getString("title")
            val publisher: String = jsonObj.getString("publisher")
            val articleUrl: String = jsonObj.getString("articleUrl")
            val imageUrl: String = jsonObj.getString("imageUrl")
            val summary: String = jsonObj.getString("summary")
            val date = DateConverter.fromIsoString(jsonObj.getString("date"))

            return NewsEntry(title, publisher, articleUrl, imageUrl, summary, date)
        }
    }
}
