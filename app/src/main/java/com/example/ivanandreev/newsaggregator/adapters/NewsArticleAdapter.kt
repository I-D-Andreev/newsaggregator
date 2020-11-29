package com.example.ivanandreev.newsaggregator.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ivanandreev.newsaggregator.R
import com.example.ivanandreev.newsaggregator.fragments.NewsEntry
import com.example.ivanandreev.newsaggregator.helpers.RWFile
import com.example.ivanandreev.newsaggregator.json.JsonSavedArticles
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.saved_article_entry.view.*
import java.text.SimpleDateFormat
import java.util.*


class NewsArticleAdapter(
    private val newsArticlesList: MutableList<NewsEntry>,
    private val context: Context
) :
    RecyclerView.Adapter<NewsArticleAdapter.ViewHolder>() {
    private val savedArticlesFileName = context.getString(R.string.saved_articles_file)
    private lateinit var currentSavedArticles: JsonSavedArticles

    inner class ViewHolder(val layout: View) : RecyclerView.ViewHolder(layout) {
        init {
            layout.setOnClickListener(this::onItemClicked)
        }

        private fun onItemClicked(view: View) {
            val actions: Array<String> = context.resources.getStringArray(R.array.news_article_actions)
            val articlePosition: Int = this.layoutPosition

            val dialog: AlertDialog = AlertDialog.Builder(view.context)
                .setTitle(context.getString(R.string.choose_action))
                .setItems(actions) { _: DialogInterface?, which: Int ->
                    when (which) {
                        0 -> visitArticle(articlePosition)
                        1 -> addArticle(articlePosition)
                    }
                }
                .setNegativeButton(context.getString(R.string.cancel), null)
                .create()
            dialog.show()
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        currentSavedArticles = loadSavedArticles()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        saveSavedArticlesToFile()
    }


    private fun loadSavedArticles(): JsonSavedArticles {
        println("!!! Articles loaded into variable")
        val currentData: String = RWFile.readFromFile(savedArticlesFileName, context)
        return if (currentData.isNotEmpty()) JsonSavedArticles(currentData) else JsonSavedArticles()
    }

    private fun addArticle(position: Int) {
        val article: NewsEntry = newsArticlesList[position]
        println("!!! AddArticle::article = ${article.publisher}")
        if(!currentSavedArticles.contains(article)) {
            currentSavedArticles.addArticle(article)
        }
    }

    private fun saveSavedArticlesToFile() {
        println("!!! Articles saved to file")
        RWFile.writeToFile(savedArticlesFileName, currentSavedArticles.toJsonArrayString(), context)
    }

    private fun visitArticle(position: Int) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.news_article_entry, parent, false)
        return ViewHolder(view)
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