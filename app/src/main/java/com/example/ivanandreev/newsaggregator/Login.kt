package com.example.ivanandreev.newsaggregator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth


class Login : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)
        mAuth = FirebaseAuth.getInstance()

    }

    private fun signIn(){
        val email_textbox = findViewById<TextInputEditText>(R.id.email)
        val password_textbox = findViewById<TextInputEditText>(R.id.password)

        val email = email_textbox.text
        val password = password_textbox.text
//        mAuth.createUserWithEmailAndPassword(email, )
    }
}