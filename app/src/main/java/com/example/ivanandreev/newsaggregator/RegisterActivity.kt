package com.example.ivanandreev.newsaggregator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen)
    }

    fun createAccount(view: View){
        println("!!! Hello world!")
    }

}