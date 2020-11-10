package com.example.ivanandreev.newsaggregator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

// user: t@t.com
// password: 123456

class Login : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)
        mAuth = FirebaseAuth.getInstance()
    }

    fun signIn(view: View) {
        val emailTextbox = findViewById<TextInputEditText>(R.id.email)
        val passwordTextbox = findViewById<TextInputEditText>(R.id.password)

        val email: String = emailTextbox.text.toString()
        val password: String = passwordTextbox.text.toString()

        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    println("Sign in successful")
                    val user = mAuth!!.currentUser
                    logInSuccess(user)
                } else {
                    // If sign in fails, display a message to the user.
                    print("Sign in failure: ${task.exception}")
                    hideKeyboard()
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun logInSuccess(user: FirebaseUser?){
        val intent = Intent(this, AccountPreferences::class.java)
        startActivity(intent)
    }

    private fun hideKeyboard(){
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken,0)
    }

}