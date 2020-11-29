package com.example.ivanandreev.newsaggregator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import com.example.ivanandreev.newsaggregator.R
import com.example.ivanandreev.newsaggregator.helpers.Keyboard


class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(
        R.layout.search_screen,
        container,
        false
    )!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchButton = view.findViewById<AppCompatImageButton>(R.id.search_button)
        searchButton.setOnClickListener(this::onSearchButtonClicked)
    }

    private fun onSearchButtonClicked(view: View){
        Keyboard.hideKeyboard(view)
    }
}