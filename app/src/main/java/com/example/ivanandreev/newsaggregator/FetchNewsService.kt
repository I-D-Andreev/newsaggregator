package com.example.ivanandreev.newsaggregator

import android.app.Service
import android.content.Context
import android.content.Intent
import java.util.*
import android.os.IBinder
import com.koushikdutta.ion.Ion
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat

class FetchNewsService : Service() {
    private val tempNewsFileName = "tempNews.txt"

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread(Runnable {
            val url: String = buildAPICall()
            println("!!! URL is $url")
//            val newsJSONString = Ion.with(this)
//                .load("GET", url)
//                .setHeader("user-agent", "insomnia/2020.4.1")
//                .asString().get()

            val newsJSONString = "Hello World Hello World Hello"
            val fileOut = openFileOutput(tempNewsFileName, Context.MODE_PRIVATE)
            val writer = OutputStreamWriter(fileOut)
            writer.write(newsJSONString)
            writer.close()

            stopSelf()
        }).start()

        return START_STICKY
    }

    private fun buildAPICall(): String {
        val domain: String = "http://newsapi.org/v2/everything?"
        val dateRange: String = dateRangeAPI()
        val language: String = "language=en"
        val newsDomains: String = joinNewsDomainsAPI(
            resources.getStringArray(R.array.news_sites_domains_list)
        )
        val pageSize = "pageSize=100"
        val apiKey = "apiKey=${getString(R.string.news_api_key)}"

        return "$domain$dateRange&$language&$newsDomains&$pageSize&$apiKey"
    }

    private fun dateRangeAPI(): String {
        val today = Calendar.getInstance()
        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DAY_OF_YEAR, -1)

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
        // get only from today?
        return "from=${sdf.format(yesterday.time)}&to=${sdf.format(today.time)}"
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
}