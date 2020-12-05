package com.example.ivanandreev.newsaggregator

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.example.ivanandreev.newsaggregator.firebase.FireDB
import com.example.ivanandreev.newsaggregator.helpers.DateConverter
import com.example.ivanandreev.newsaggregator.helpers.RWFile
import com.example.ivanandreev.newsaggregator.json.JsonNews
import java.util.*

class FetchNewsService : Service() {
    // We want this variable private for the class as it is really implementation dependant, so
    // the string is not exported.
    private val tempNewsFileName = "tempNews.txt"
    private val fireDB = FireDB(FireDB.USER_KEYWORDS)

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val sendNotifications: Boolean =
            intent!!.extras!!.getBoolean(getString(R.string.send_notifications_field))


        println("!!! In Service. Time = ${Date()}")
        Thread(Runnable {
            val url: String = buildAPICall()
            println("!!! URL is $url")
//            val newsJSONString = Ion.with(this)
//                .load("GET", url)
//                .setHeader("user-agent", "insomnia/2020.4.1")
//                .asString().get()
            val newsJSONString = populateDummyData()
            RWFile.writeToFile(tempNewsFileName, newsJSONString, this)
            if (sendNotifications) {
                sendNotifications(JsonNews(newsJSONString))
            }

            stopSelf()
        }).start()

        return START_STICKY
    }

    private fun sendNotifications(news: JsonNews) {
        // Get the date of the previous newest article. Get the current keywords. Filter the
        // new articles. Find how many articles are newer than the previous newest.
        // Send a notification and update the newest article date.
        val sharedPreferences =
            getSharedPreferences(
                getString(R.string.shared_preferences_db_name),
                Context.MODE_PRIVATE
            )

        val previousNewestArticleDateISO = sharedPreferences.getString(
            getString(R.string.newest_article_date_key),
            getString(R.string.date_1970_iso)
        )

        val previousNewestArticleDate: Calendar = DateConverter.fromIsoString(previousNewestArticleDateISO!!)




//        val articles: ArrayList<NewsEntry> = ArticlesFilter.filterArticles()
    }

    // for testing purposes only, so as not to use up API calls
    private fun populateDummyData(): String {
        // contains Article 0 Title to Article 11 Title
        return """
            {"status":"ok","totalResults":12,"articles":[{"source":{"id":"bbc-news","name":"BBCNews"},"author":null,"title":"Article 0 Title","description":"Description 0","url":"https://www.bbc.co.uk/news/av/world-us-canada-54937355","urlToImage":"https://ichef.bbci.co.uk/images/ic/400xn/p08yffwk.jpg","publishedAt":"2020-11-14T01:01:17Z",
            "content":"AviralvideoofahugealligatorstrollingonagolfcourseinNaples,Florida,hasleftmanypeoplequestioningwhetherit'srealornot.\r\nSoweaskedgatorexpertJamesNifongfromtheUniversity…[+35chars]"},{"source":{"id":"bbc-news","name":"BBCNews"},"author":null,"title":"Article 1 Title","description":"Description 1","url":"https://www.bbc.co.uk/news/av/world-us-canada-54937355",
            "urlToImage":"https://ichef.bbci.co.uk/images/ic/400xn/p08yffwk.jpg","publishedAt":"2020-11-14T01:01:17Z","content":"AviralvideoofahugealligatorstrollingonagolfcourseinNaples,Florida,hasleftmanypeoplequestioningwhetherit'srealornot.\r\nSoweaskedgatorexpertJamesNifongfromtheUniversity…[+35chars]"},{"source":{"id":"bbc-news","name":"BBCNews"},"author":null,
            "title":"Article 2 Title","description":"Description 2","url":"https://www.bbc.co.uk/news/av/world-us-canada-54937355","urlToImage":"https://ichef.bbci.co.uk/images/ic/400xn/p08yffwk.jpg","publishedAt":"2020-11-14T01:01:17Z","content":"AviralvideoofahugealligatorstrollingonagolfcourseinNaples,Florida,hasleftmanypeoplequestioningwhetherit'srealornot.\r\nSoweaskedgatorexpertJamesNifongfromtheUniversity…[+35chars]"},
            {"source":{"id":"bbc-news","name":"The Independent"},"author":null,"title":"Article 3 Title","description":"Description 3","url":"https://www.bbc.co.uk/news/av/world-us-canada-54937355","urlToImage":"https://ichef.bbci.co.uk/images/ic/400xn/p08yffwk.jpg","publishedAt":"2020-11-14T01:01:17Z","content":"AviralvideoofahugealligatorstrollingonagolfcourseinNaples,Florida,hasleftmanypeoplequestioningwhetherit'srealornot.\r\nSoweaskedgatorexpertJamesNifongfromtheUniversity…[+35chars]"},{"source":{"id":"bbc-news","name":"TheGuardian"},
            "author":null,"title":"Article 4 Title","description":"Description 4","url":"https://www.bbc.co.uk/news/av/world-us-canada-54937355","urlToImage":"https://ichef.bbci.co.uk/images/ic/400xn/p08yffwk.jpg","publishedAt":"2020-11-14T01:01:17Z","content":"AviralvideoofahugealligatorstrollingonagolfcourseinNaples,Florida,hasleftmanypeoplequestioningwhetherit'srealornot.\r\nSoweaskedgatorexpertJamesNifongfromtheUniversity…[+35chars]"},{"source":{"id":"bbc-news","name":"BBCNews"},"author":null,"title":"Article 5 Title","description":"Description 5","url":"https://www.bbc.co.uk/news/av/world-us-canada-54937355","urlToImage":"https://ichef.bbci.co.uk/images/ic/400xn/p08yffwk.jpg","publishedAt":"2020-11-14T01:01:17Z","content":"AviralvideoofahugealligatorstrollingonagolfcourseinNaples,Florida,hasleftmanypeoplequestioningwhetherit'srealornot.\r\nSoweaskedgatorexpertJamesNifongfromtheUniversity…[+35chars]"},{"source":{"id":"bbc-news","name":"BBCNews"},"author":null,"title":"Article 6 Title","description":"Description 6","url":"https://www.bbc.co.uk/news/av/world-us-canada-54937355","urlToImage":"https://ichef.bbci.co.uk/images/ic/400xn/p08yffwk.jpg",
            "publishedAt":"2020-11-14T01:01:17Z","content":"AviralvideoofahugealligatorstrollingonagolfcourseinNaples,Florida,hasleftmanypeoplequestioningwhetherit'srealornot.\r\nSoweaskedgatorexpertJamesNifongfromtheUniversity…[+35chars]"},{"source":{"id":"bbc-news","name":"BBCNews"},"author":null,"title":"Article 7 Title","description":"Description 7","url":"https://www.bbc.co.uk/news/av/world-us-canada-54937355","urlToImage":"https://ichef.bbci.co.uk/images/ic/400xn/p08yffwk.jpg","publishedAt":"2020-11-14T01:01:17Z","content":"AviralvideoofahugealligatorstrollingonagolfcourseinNaples,Florida,hasleftmanypeoplequestioningwhetherit'srealornot.\r\nSoweaskedgatorexpertJamesNifongfromtheUniversity…[+35chars]"},
            {"source":{"id":"bbc-news","name":"BBCNews"},"author":null,"title":"Article 8 Title","description":"Description 8","url":"https://www.bbc.co.uk/news/av/world-us-canada-54937355","urlToImage":"https://ichef.bbci.co.uk/images/ic/400xn/p08yffwk.jpg","publishedAt":"2020-11-14T01:01:17Z","content":"AviralvideoofahugealligatorstrollingonagolfcourseinNaples,Florida,hasleftmanypeoplequestioningwhetherit'srealornot.\r\nSoweaskedgatorexpertJamesNifongfromtheUniversity…[+35chars]"},{"source":{"id":"bbc-news","name":"BBCNews"},"author":null,"title":"Article 9 Title","description":"Description 9","url":"https://www.bbc.co.uk/news/av/world-us-canada-54937355","urlToImage":"https://ichef.bbci.co.uk/images/ic/400xn/p08yffwk.jpg","publishedAt":"2020-11-14T01:01:17Z","content":"AviralvideoofahugealligatorstrollingonagolfcourseinNaples,Florida,hasleftmanypeoplequestioningwhetherit'srealornot.\r\nSoweaskedgatorexpertJamesNifongfromtheUniversity…[+35chars]"},{"source":{"id":"bbc-news","name":"BBCNews"},"author":null,"title":"Article 10 Title","description":"Description 10","url":"https://www.bbc.co.uk/news/av/world-us-canada-54937355",
            "urlToImage":"https://ichef.bbci.co.uk/images/ic/400xn/p08yffwk.jpg","publishedAt":"2020-11-14T01:01:17Z","content":"AviralvideoofahugealligatorstrollingonagolfcourseinNaples,Florida,hasleftmanypeoplequestioningwhetherit'srealornot.\r\nSoweaskedgatorexpertJamesNifongfromtheUniversity…[+35chars]"},{"source":{"id":"bbc-news","name":"BBCNews"},"author":null,"title":"Article 11 Title","description":"Description 11","url":"https://www.bbc.co.uk/news/av/world-us-canada-54937355","urlToImage":"https://ichef.bbci.co.uk/images/ic/400xn/p08yffwk.jpg","publishedAt":"2020-11-14T01:01:17Z","content":"AviralvideoofahugealligatorstrollingonagolfcourseinNaples,Florida,hasleftmanypeoplequestioningwhetherit'srealornot.\r\nSoweaskedgatorexpertJamesNifongfromtheUniversity…[+35chars]"}]}
        """
    }

    private fun buildAPICall(): String {
        val domain: String = "http://newsapi.org/v2/everything?"
        val dateRange: String = dateRangeAPI()
        println("!!! Date range is: $dateRange")
        val language: String = "language=en"
        val newsDomains: String = joinNewsDomainsAPI(
            resources.getStringArray(R.array.news_sites_domains_list)
        )
        val pageSize = "pageSize=100"
        val apiKey = "apiKey=${getString(R.string.news_api_key)}"

        return "$domain$dateRange&$language&$newsDomains&$pageSize&$apiKey"
    }

    private fun dateRangeAPI(): String {
        val now = Calendar.getInstance()
        val from = Calendar.getInstance()
        from.add(Calendar.HOUR, -12)

        return "from=${DateConverter.toIsoString(from)}&to=${DateConverter.toIsoString(now)}"
    }

    private fun joinNewsDomainsAPI(publishers: Array<String>): String {
        var domains = "domains="
        publishers.forEachIndexed { index, publisher ->
            domains += publisher

            if (index != publishers.count() - 1) {
                domains += ","
            }
        }
        return domains
    }

    override fun onDestroy() {
        super.onDestroy()

        // On main thread!
        // Delete the news.txt file and rename the tempNews.txt file to become the new news.txt
        // Done to protect against simultaneous reading and writing to the news.txt file.
        val newsFileName = getString(R.string.news_file)
        deleteFile(newsFileName)

        // rename tempNews.txt to news.txt
        val oldFile = getFileStreamPath(tempNewsFileName)
        val newFile = getFileStreamPath(newsFileName)
        oldFile.renameTo(newFile)
    }

    companion object {
        fun triggerFetch(context: Context, sendNotifications: Boolean) {
            val serviceIntent = Intent(context, FetchNewsService::class.java)
            serviceIntent.putExtra(
                context.getString(R.string.send_notifications_field),
                sendNotifications
            )
            context.startService(serviceIntent)
        }
    }
}