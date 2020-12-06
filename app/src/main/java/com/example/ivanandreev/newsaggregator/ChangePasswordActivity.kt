package com.example.ivanandreev.newsaggregator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class ChangePasswordActivity : AppCompatActivity() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password_screen)
    }

    fun onPasswordChangeClicked(view: View){
        println("!!! Hello world")
    }

    fun onBackButtonClicked(view: View){
        finish()
    }
}