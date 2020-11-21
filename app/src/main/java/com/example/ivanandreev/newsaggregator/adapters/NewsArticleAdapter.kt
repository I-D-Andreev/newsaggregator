package com.example.ivanandreev.newsaggregator.adapters

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ivanandreev.newsaggregator.*
import com.example.ivanandreev.newsaggregator.fragments.NewsEntry
import com.google.android.material.textview.MaterialTextView
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.saved_article_entry.view.*
import java.text.SimpleDateFormat
import java.util.*


class NewsArticleAdapter(private val newsArticlesList: MutableList<NewsEntry>) :
    RecyclerView.Adapter<NewsArticleAdapter.ViewHolder>() {

    inner class ViewHolder(var layout: View) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.news_article_entry, parent, false)
        view.setOnClickListener(this::onItemClicked)
        return ViewHolder(view)
    }

    private fun onItemClicked(view: View){
        println("!!! Item clicked")
        println("!!! View is $view")
        println("!!! Text is ${view.findViewById<MaterialTextView>(R.id.article_title).text}")

        val possibilities: Array<String> = arrayOf("Visit", "Save")
        val dialog: AlertDialog = AlertDialog.Builder(view.context)
            .setTitle("Choose an Action")
            .setItems(possibilities) {dialog: DialogInterface?, which: Int ->
                println("!!! The user clicked on ${possibilities[which]}")
            }
            .setNegativeButton(view.context.getString(R.string.cancel), null)
            .create()
        dialog.show()
    }

    override fun getItemCount(): Int {
        return newsArticlesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = newsArticlesList[position]
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.UK)

        Ion.with(holder.layout.image)
            .placeholder(R.drawable.white)
            .error(R.drawable.error)
            .load(article.imageUrl)

        holder.layout.article_title.text = article.title
        holder.layout.article_date.text = sdf.format(article.date.time)
        holder.layout.article_publisher.text = article.publisher
    }

}