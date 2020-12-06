package com.example.ivanandreev.newsaggregator

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ivanandreev.newsaggregator.helpers.Keyboard
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class ChangePasswordActivity : AppCompatActivity() {
    private val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password_screen)
    }

    override fun onResume() {
        super.onResume()
        clearAllFields()
    }

    private fun clearAllFields() {
        val oldPasswordTextBox = findViewById<TextInputEditText>(R.id.old_password)
        val newPasswordTextBox = findViewById<TextInputEditText>(R.id.new_password)
        val repeatPasswordTextBox = findViewById<TextInputEditText>(R.id.repeat_password)

        oldPasswordTextBox.text?.clear()
        newPasswordTextBox.text?.clear()
        repeatPasswordTextBox.text?.clear()
        oldPasswordTextBox.requestFocus()
    }

    fun onPasswordChangeClicked(view: View) {
        Keyboard.hideKeyboard(view)

        val oldPassword = findViewById<TextInputEditText>(R.id.old_password).text.toString()
        val newPassword = findViewById<TextInputEditText>(R.id.new_password).text.toString()
        val repeatPassword = findViewById<TextInputEditText>(R.id.repeat_password).text.toString()

        if (oldPassword.isEmpty()) {
            Toast.makeText(this, "Old password field is empty!", Toast.LENGTH_SHORT).show()
            return
        }

        if (user != null) {
            val credentials: AuthCredential =
                EmailAuthProvider.getCredential(user.email!!, oldPassword)

            user.reauthenticate(credentials).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    when {
                        newPassword.isEmpty() or repeatPassword.isEmpty() -> {
                            Toast.makeText(
                                this,
                                "New password or Repeat Password can't be empty!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        newPassword != repeatPassword -> {
                            Toast.makeText(this, "Passwords must match!", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            user.updatePassword(newPassword).addOnSuccessListener { _ ->
                                Toast.makeText(
                                    this, "Passwords changed successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                clearAllFields()
                            }.addOnFailureListener { ex ->
                                Toast.makeText(
                                    this, "Failed to change password! ${ex.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "Wrong password!", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    fun onBackButtonClicked(view: View) {
        finish()
    }
}