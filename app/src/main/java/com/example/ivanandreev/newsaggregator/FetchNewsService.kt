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
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread(Runnable {
            val url: String = buildAPICall()
            val newsJSONString = Ion.with(this)
                .load("GET", url)
                .setHeader("user-agent", "insomnia/2020.4.1")
                .asString().get()

            val fileOut = openFileOutput(getString(R.string.news_file), Context.MODE_PRIVATE)
            val writer = OutputStreamWriter(fileOut)
            writer.write(newsJSONString)
            writer.close()
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
}