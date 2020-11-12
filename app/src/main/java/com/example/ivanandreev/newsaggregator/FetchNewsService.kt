package com.example.ivanandreev.newsaggregator

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import java.io.OutputStreamWriter

class FetchNewsService : Service(){
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread(Runnable {
            val fileOut = openFileOutput(getString(R.string.news_file), Context.MODE_PRIVATE)
            val writer = OutputStreamWriter(fileOut)
            writer.write("Hello world!!")
            writer.close()
        }).start()
        return START_STICKY
    }
}