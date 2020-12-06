package com.example.ivanandreev.newsaggregator.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ivanandreev.newsaggregator.R
import com.example.ivanandreev.newsaggregator.fragments.NewsEntry
import com.example.ivanandreev.newsaggregator.helpers.ArticleReader
import com.example.ivanandreev.newsaggregator.helpers.DateConverter
import com.example.ivanandreev.newsaggregator.helpers.RWFile
import com.example.ivanandreev.newsaggregator.json.JsonSavedArticles
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.saved_article_entry.view.*


class NewsArticleAdapter(
    private val newsArticlesList: MutableList<NewsEntry>,
    private val context: Context
) :
    RecyclerView.Adapter<NewsArticleAdapter.ViewHolder>() {
    private val savedArticlesFileName = context.getString(R.string.saved_articles_file)
    private lateinit var currentSavedArticles: JsonSavedArticles
    private val logTag = NewsArticleAdapter::class.java.simpleName

    inner class ViewHolder(val layout: View) : RecyclerView.ViewHolder(layout) {
        init {
            layout.setOnClickListener(this::onItemClicked)
        }

        private fun onItemClicked(view: View) {
            val articlePosition: Int = this.layoutPosition
            val article = newsArticlesList[articlePosition]

            val dialog: AlertDialog = AlertDialog.Builder(view.context)
                .setTitle(context.getString(R.string.choose_action))
                .setMessage(article.summary)
                .setNeutralButton(context.getString(R.string.read_article)) { _: DialogInterface, _: Int ->
                    readArticle(article)
                }
                .setPositiveButton(context.getString(R.string.save_article)) { _: DialogInterface, _: Int ->
                    addArticle(article)
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
        Log.i(logTag, "Saved Articles loaded into variable")
        val currentData: String = RWFile.readFromFile(savedArticlesFileName, context)
        return if (currentData.isNotEmpty()) JsonSavedArticles(currentData) else JsonSavedArticles()
    }

    private fun addArticle(article: NewsEntry) {
        val message: String = if (!currentSavedArticles.contains(article)) {
            currentSavedArticles.addArticle(article)
            context.getString(R.string.article_saved)
        } else {
            context.getString(R.string.article_already_saved)
        }

        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 200)
        toast.show()
    }

    private fun saveSavedArticlesToFile() {
        Log.i(logTag, "Saved Articles saved to file")
        RWFile.writeToFile(savedArticlesFileName, currentSavedArticles.toJsonArrayString(), context)
    }

    private fun readArticle(article: NewsEntry) {
        ArticleReader.readArticle(article.articleUrl, context)
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

        Ion.with(holder.layout.image)
            .placeholder(R.drawable.white)
            .error(R.drawable.error)
            .load(article.imageUrl)
        holder.layout.article_title.text = article.title
        holder.layout.article_date.text = DateConverter.toDayMonthYearString(article.date)
        holder.layout.article_publisher.text = article.publisher
    }


}