package com.example.ivanandreev.newsaggregator.helpers

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

class Keyboard {
    companion object {
        fun hideKeyboard(currentView: View) {
            val imm =
                currentView.context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentView.windowToken, 0)
        }
    }
}