package com.example.ivanandreev.newsaggregator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ivanandreev.newsaggregator.helpers.Keyboard
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

// user: t@t.com
// password: 123456

class LoginActivity : AppCompatActivity() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)
        startNewsFetchService()
    }

    private fun startNewsFetchService() {
        val serviceIntent = Intent(this, FetchNewsService::class.java)
        startService(serviceIntent)
    }

    fun registerHere(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun signIn(view: View) {
        Keyboard.hideKeyboard(this, view)

        val emailTextBox = findViewById<TextInputEditText>(R.id.email)
        val passwordTextBox = findViewById<TextInputEditText>(R.id.password)

        val email: String = emailTextBox.text.toString()
        val password: String = passwordTextBox.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        println("!!! Sign in successful")
                        emailTextBox.text?.clear()
                        passwordTextBox.text?.clear()

                        emailTextBox.clearFocus()
                        passwordTextBox.clearFocus()

                        logInSuccess()
                    } else {
                        // If sign in fails, display a message to the user.
                        print("!!! Sign in failure: ${task.exception}")
                        Toast.makeText(this, getString(R.string.authentication_failed), Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, getString(R.string.username_and_password_no_empty), Toast.LENGTH_SHORT).show()
        }
    }

    private fun logInSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}