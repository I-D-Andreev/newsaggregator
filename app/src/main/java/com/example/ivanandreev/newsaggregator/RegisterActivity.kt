package com.example.ivanandreev.newsaggregator

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ivanandreev.newsaggregator.helpers.Keyboard
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


class RegisterActivity : AppCompatActivity() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val logTag = RegisterActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen)
    }

    fun createAccount(view: View) {
        Keyboard.hideKeyboard(view)

        val emailTextBox = findViewById<TextInputEditText>(R.id.email)
        val passwordTextBox = findViewById<TextInputEditText>(R.id.password)
        val repeatPasswordTextBox = findViewById<TextInputEditText>(R.id.repeat_password)

        val email = emailTextBox.text.toString()
        val password = passwordTextBox.text.toString()
        val repeatPassword = repeatPasswordTextBox.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()) {
            if (password == repeatPassword) {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {task: Task<AuthResult> ->
                    if(task.isSuccessful){
                        Log.i(logTag, "User creation is successful!")
                        signInUser()
                    } else{
                        Toast.makeText(this, getString(R.string.authentication_failed), Toast.LENGTH_SHORT).show()
                    }

                }
            } else {
                Toast.makeText(this, getString(R.string.passwords_must_match), Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(this, getString(R.string.all_fields_filled), Toast.LENGTH_SHORT).show()
        }
    }

    private fun signInUser(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}