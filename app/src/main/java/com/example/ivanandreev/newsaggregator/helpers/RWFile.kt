package com.example.ivanandreev.newsaggregator.helpers

import android.content.Context
import android.util.Log
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import com.google.firebase.auth.FirebaseAuth

class RWFile {
    companion object {
        private val logTag = RWFile::class.java.simpleName

        fun readFromFile(fileName: String, ctx: Context): String {
            var data: String = ""
            try {
                val fileIn = ctx.openFileInput(fileName)
                val fileReader = InputStreamReader(fileIn)
                data = fileReader.readText()
                fileReader.close()
            } catch (e: FileNotFoundException) {
                Log.i(logTag, "$fileName not found. Returning empty data.")
            }
            return data
        }

        fun writeToFile(fileName: String, data: String, ctx: Context) {
            val fileOut = ctx.openFileOutput(fileName, Context.MODE_PRIVATE)
            val writer = OutputStreamWriter(fileOut)
            writer.write(data)
            writer.close()
        }

        fun readFromFilePersonalized(fileName: String, ctx: Context): String {
            val userEmail: String? = FirebaseAuth.getInstance().currentUser?.email
            val userFileName = "${userEmail}_$fileName"
            return readFromFile(userFileName, ctx)
        }

        fun writeToFilePersonalized(fileName: String, data: String, ctx: Context) {
            val userEmail: String? = FirebaseAuth.getInstance().currentUser?.email
            val userFileName = "${userEmail}_$fileName"
            return writeToFile(userFileName, data, ctx)
        }

    }
}
