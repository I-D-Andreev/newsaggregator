package com.example.ivanandreev.newsaggregator

import android.app.Service
import android.content.Context
import android.content.Intent
import java.util.*
import android.os.IBinder
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat

class FetchNewsService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread(Runnable {
            buildAPICall()

            val fileOut = openFileOutput(getString(R.string.news_file), Context.MODE_PRIVATE)
            val writer = OutputStreamWriter(fileOut)
            writer.write("Hello world!!")
            writer.close()
        }).start()
        return START_STICKY
    }

    private fun buildAPICall() {
        val domain: String = "http://newsapi.org/v2/everything?"
        val dateRange: String = yesterdayToTodayAPI()
        val language: String = "language=en"
        val newsDomains: String = joinNewsDomainsAPI(
            resources.getStringArray(R.array.news_sites_domains_list)
        )
        val apiKey = "apiKey=${getString(R.string.news_api_key)}"

        val url = "$domain$dateRange&$language&$newsDomains&$apiKey"

        println("!!!!!$url")
    }

    private fun yesterdayToTodayAPI(): String {
        val today = Calendar.getInstance()
        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DAY_OF_YEAR, -1)

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
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