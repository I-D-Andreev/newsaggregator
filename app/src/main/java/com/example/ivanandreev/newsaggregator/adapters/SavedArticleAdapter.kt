package com.example.ivanandreev.newsaggregator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ivanandreev.newsaggregator.*
import com.example.ivanandreev.newsaggregator.fragments.SavedArticleEntry
import kotlinx.android.synthetic.main.saved_article_entry.view.*
import java.text.SimpleDateFormat
import java.util.*


class SavedArticleAdapter(private val savedArticlesList: MutableList<SavedArticleEntry>) :
    RecyclerView.Adapter<SavedArticleAdapter.ViewHolder>() {

    inner class ViewHolder(var layout: View) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.saved_article_entry, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return savedArticlesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = savedArticlesList[position]
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.US)

        holder.layout.image.setImageResource(article.image)
        holder.layout.article_title.text = article.title
        holder.layout.article_date.text = sdf.format(article.date.time)
        holder.layout.article_publisher.text = article.publisher

        holder.layout.remove.setOnClickListener {
            Toast.makeText(holder.layout.context, "Delete hit!", Toast.LENGTH_LONG).show()
        }
    }

}