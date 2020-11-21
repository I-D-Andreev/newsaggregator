package com.example.ivanandreev.newsaggregator.helpers

import android.content.Context
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class RWFile{
    companion object {
        fun readFromFile(fileName: String, ctx: Context): String{
            val fileIn = ctx.openFileInput(fileName)
            val fileReader = InputStreamReader(fileIn)
            val data = fileReader.readText()
            fileReader.close()
            return data
        }

        fun writeToFile(fileName: String, data: String, ctx: Context){
            val fileOut = ctx.openFileOutput(fileName, Context.MODE_PRIVATE)
            val writer = OutputStreamWriter(fileOut)
            writer.write(data)
            writer.close()
        }
    }
}
