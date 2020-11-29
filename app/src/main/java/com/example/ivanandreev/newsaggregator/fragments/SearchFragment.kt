package com.example.ivanandreev.newsaggregator.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ivanandreev.newsaggregator.R
import com.example.ivanandreev.newsaggregator.adapters.NewsArticleAdapter
import com.example.ivanandreev.newsaggregator.helpers.Keyboard
import com.example.ivanandreev.newsaggregator.helpers.RWFile
import com.example.ivanandreev.newsaggregator.json.JsonNews
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : Fragment() {
    private val maxArticlesShown: Int = 10

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
        val searchTextBox = view.findViewById<TextInputEditText>(R.id.search_text_box)
        val searchButton = view.findViewById<AppCompatImageButton>(R.id.search_button)
        val addToKeywordsButton = view.findViewById<MaterialButton>(R.id.add_to_keywords)

        searchButton.setOnClickListener(this::onSearchButtonClicked)
        searchTextBox.setOnKeyListener { _, keyCode: Int, event: KeyEvent ->
            onTextChanged(keyCode, event, searchButton)
        }

        addToKeywordsButton.setOnClickListener { v: View ->
            addCurrentSearchToKeywords(v, searchTextBox)
        }
    }

    override fun onStop() {
        super.onStop()
        // Trigger adapter detach callback
        view!!.findViewById<RecyclerView>(R.id.search_recyclerview)!!.adapter = null
    }

    private fun addCurrentSearchToKeywords(view: View, searchTextBox: TextInputEditText) {
    }

    private fun onTextChanged(
        keyCode: Int,
        event: KeyEvent,
        searchButton: AppCompatImageButton
    ): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
            searchButton.performClick()
        }
        return true
    }

    private fun onSearchButtonClicked(view: View) {
        val searchBox = view.rootView.findViewById<TextInputEditText>(R.id.search_text_box)
        val dividingLine = view.rootView.findViewById<View>(R.id.dividing_line)

        val searchPhrase = searchBox.text.toString()

        // Need to shift focus to another element in order to "un-focus" the search text box
        // as there needs to be a focused element and clearFocus() will just refocus the search
        // text box again...
        dividingLine.requestFocus()

        Keyboard.hideKeyboard(view)

        loadRecyclerView(view, searchPhrase)
    }

    private fun loadRecyclerView(view: View, searchPhrase: String) {
        val articlesList: ArrayList<NewsEntry> = populateData(searchPhrase)
        val recyclerView = view.rootView.findViewById<RecyclerView>(R.id.search_recyclerview)
        val layoutManager = LinearLayoutManager(context)
        val recyclerAdapter = NewsArticleAdapter(articlesList, context!!)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerAdapter
    }

    private fun populateData(searchPhrase: String): ArrayList<NewsEntry> {
        val jsonNewsString: String = RWFile.readFromFile(getString(R.string.news_file), context!!)
        val news = JsonNews(jsonNewsString)

        val articles = ArrayList<NewsEntry>()
        for (jsonArticle in news.articles) {
            if (searchPhrase.toLowerCase(Locale.getDefault()) in
                jsonArticle.title.toLowerCase(Locale.getDefault())
            ) {
                articles.add(
                    NewsEntry(
                        jsonArticle.title,
                        jsonArticle.publisher,
                        jsonArticle.url,
                        jsonArticle.urlToImage,
                        jsonArticle.publishedAt
                    )
                )
            }

            if (articles.size >= maxArticlesShown) {
                break
            }
        }

        return articles
    }


}