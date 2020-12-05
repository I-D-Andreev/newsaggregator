package com.example.ivanandreev.newsaggregator.helpers

import com.example.ivanandreev.newsaggregator.fragments.NewsEntry
import com.example.ivanandreev.newsaggregator.json.JsonArticle
import com.example.ivanandreev.newsaggregator.json.JsonNews
import java.util.*
import kotlin.collections.ArrayList

class ArticlesFilter {
    companion object {
        fun filterArticles(
            news: JsonNews,
            keywords: ArrayList<String>,
            maxArticles: Int
        ): ArrayList<NewsEntry> {
            val articles = ArrayList<NewsEntry>()

            // firstly add articles that contain a certain keyword
            for (article: JsonArticle in news.articles) {
                for (keyword: String in keywords) {
                    if (keyword.toLowerCase(Locale.getDefault()) in article.title.toLowerCase(Locale.getDefault())) {
                        articles.add(
                            NewsEntry(
                                article.title,
                                article.publisher,
                                article.url,
                                article.urlToImage,
                                article.publishedAt
                            )
                        )
                        break
                    }
                }

                if (articles.size >= maxArticles) {
                    break
                }
            }

            // then, if we don't have enough articles, fill in the rest with any of the other articles
            for (article: JsonArticle in news.articles) {
                if (articles.size >= maxArticles) {
                    break
                }

                val entry = NewsEntry(
                    article.title,
                    article.publisher,
                    article.url,
                    article.urlToImage,
                    article.publishedAt
                )

                if (!articles.contains(entry)) {
                    articles.add(entry)
                }
            }

            return articles
        }

        fun findNewestArticle(articles: ArrayList<NewsEntry>): NewsEntry? {
            if (articles.size == 0) {
                return null
            }

            var article: NewsEntry = articles[0]
            for (entry in articles) {
                if (entry.date > article.date) {
                    article = entry
                }
            }

            return article
        }
    }
}