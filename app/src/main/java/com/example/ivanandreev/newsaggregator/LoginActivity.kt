package com.example.ivanandreev.newsaggregator

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ivanandreev.newsaggregator.helpers.AlarmReceiver
import com.example.ivanandreev.newsaggregator.helpers.Keyboard
import com.example.ivanandreev.newsaggregator.helpers.NotificationSender
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

// user: t@t.com
// password: 123456

class LoginActivity : AppCompatActivity() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)
        scheduleNewsFetchService()
    }

    override fun onResume() {
        super.onResume()
        val emailTextBox = findViewById<TextInputEditText>(R.id.email)
        val passwordTextBox = findViewById<TextInputEditText>(R.id.password)

        emailTextBox.text?.clear()
        passwordTextBox.text?.clear()
        emailTextBox.clearFocus()
        passwordTextBox.clearFocus()
    }

    fun notifButtonClick(view: View) {
        val ns = NotificationSender(view.context)
        ns.sendNotification("Hello world", "First notification")
    }

    private fun scheduleNewsFetchService() {
        val intent = Intent(applicationContext, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this, AlarmReceiver.REQUEST_CODE,
            intent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val currentMilliseconds = System.currentTimeMillis()
        val triggerIntervalMillis: Long = 5 * 60 * 1000;

        val alarm: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.setRepeating(
            AlarmManager.RTC_WAKEUP, currentMilliseconds,
            triggerIntervalMillis, pendingIntent
        )
    }

    fun registerHere(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun signIn(view: View) {
        Keyboard.hideKeyboard(view)

        val emailTextBox = findViewById<TextInputEditText>(R.id.email)
        val passwordTextBox = findViewById<TextInputEditText>(R.id.password)

        val email: String = emailTextBox.text.toString()
        val password: String = passwordTextBox.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        println("!!! Sign in successful")

                        signInUser()
                    } else {
                        print("!!! Sign in failure: ${task.exception}")
                        Toast.makeText(
                            this,
                            getString(R.string.authentication_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(
                this,
                getString(R.string.username_and_password_no_empty),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun signInUser() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}