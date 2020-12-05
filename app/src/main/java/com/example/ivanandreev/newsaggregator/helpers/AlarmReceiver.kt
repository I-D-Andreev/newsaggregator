package com.example.ivanandreev.newsaggregator.helpers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.ivanandreev.newsaggregator.FetchNewsService

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        FetchNewsService.triggerFetch(context!!, true)
    }

    companion object {
        const val REQUEST_CODE = 105
    }
}