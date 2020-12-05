package com.example.ivanandreev.newsaggregator.helpers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.ivanandreev.newsaggregator.FetchNewsService

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val serviceIntent = Intent(context, FetchNewsService::class.java)
        context?.startService(serviceIntent)
    }

    companion object {
        const val REQUEST_CODE = 105
        const val ACTION = "com.example.ivanandreev.newsaggregator.PERIODIC"
    }
}