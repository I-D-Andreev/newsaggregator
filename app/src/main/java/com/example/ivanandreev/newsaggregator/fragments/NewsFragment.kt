package com.example.ivanandreev.newsaggregator.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ivanandreev.newsaggregator.R
import com.google.android.material.textview.MaterialTextView
import java.io.InputStreamReader

class NewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(
        R.layout.news_screen,
        container,
        false
    )!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillTextBox(view)
    }

    private fun fillTextBox(view: View) {
        val txtBox = view.findViewById<MaterialTextView>(R.id.show_text)
        txtBox.text = readNewsFile()
    }

    private fun readNewsFile(): String {
        val fileIn = context?.openFileInput(getString(R.string.news_file))
        val fileReader = InputStreamReader(fileIn)
        return fileReader.readText().substring(0, 200);
    }
}