package com.example.ivanandreev.newsaggregator.adapters

import android.app.AlertDialog
import android.content.Context
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
        val ctx: Context = view.context
        val actions: Array<String> = ctx.resources.getStringArray(R.array.news_article_actions)

        val dialog: AlertDialog = AlertDialog.Builder(view.context)
            .setTitle(ctx.getString(R.string.choose_action))
            .setItems(actions) {_: DialogInterface?, which: Int ->
                when(which){
                    0 -> visitArticle()
                    1 -> saveArticle()
                }
            }
            .setNegativeButton(ctx.getString(R.string.cancel), null)
            .create()
        dialog.show()
    }

    private fun visitArticle(){

    }

    private fun saveArticle() {

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