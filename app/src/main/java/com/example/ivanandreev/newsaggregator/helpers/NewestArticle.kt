package com.example.ivanandreev.newsaggregator.helpers

import android.content.Context
import android.util.Log
import com.example.ivanandreev.newsaggregator.R
import com.example.ivanandreev.newsaggregator.fragments.NewsEntry
import java.util.*
import kotlin.collections.ArrayList

class NewestArticle {
    companion object {
        private val logTag = NewestArticle::class.java.simpleName

        fun updateNewestArticle(filteredArticlesList: ArrayList<NewsEntry>, ctx: Context) {
            val newestArticle: NewsEntry? = ArticlesFilter.findNewestArticle(filteredArticlesList)
            val newestArticleDateString: String = if (newestArticle != null) {
                DateConverter.toIsoString(newestArticle.date)
            } else {
                ctx.getString(R.string.date_1970_iso)
            }

            Log.i(logTag, "Newest article date : $newestArticleDateString")
            val sharedPreferences = ctx.getSharedPreferences(
                ctx.getString(R.string.shared_preferences_db_name),
                Context.MODE_PRIVATE
            )
            val editor = sharedPreferences.edit()
            editor.putString(
                ctx.getString(R.string.newest_article_date_key),
                newestArticleDateString
            )
            editor.apply()
        }

        fun getCurrentNewestArticle(ctx: Context) : Calendar{
            val sharedPreferences =
                ctx.getSharedPreferences(
                    ctx.getString(R.string.shared_preferences_db_name),
                    Context.MODE_PRIVATE
                )

            val currentNewestArticleDateISO = sharedPreferences.getString(
                ctx.getString(R.string.newest_article_date_key),
                ctx.getString(R.string.date_1970_iso)
            )

            Log.i(logTag, "Previous newest article date $currentNewestArticleDateISO")

            return DateConverter.fromIsoString(currentNewestArticleDateISO!!)
        }
    }
}