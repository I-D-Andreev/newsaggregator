package com.example.ivanandreev.newsaggregator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.ivanandreev.newsaggregator.R
import com.example.ivanandreev.newsaggregator.fragments.NewsEntry
import com.example.ivanandreev.newsaggregator.helpers.ArticleReader
import com.example.ivanandreev.newsaggregator.helpers.DateConverter
import com.example.ivanandreev.newsaggregator.helpers.RWFile
import com.example.ivanandreev.newsaggregator.json.JsonSavedArticles
import com.google.android.material.snackbar.Snackbar
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.saved_article_entry.view.*
import java.text.SimpleDateFormat
import java.util.*


class SavedArticleAdapter(
    private val savedArticlesList: MutableList<NewsEntry>,
    private val context: Context
) :
    RecyclerView.Adapter<SavedArticleAdapter.ViewHolder>() {
    private val savedArticlesFileName = context.getString(R.string.saved_articles_file)

    inner class ViewHolder(var layout: View) : RecyclerView.ViewHolder(layout) {
        init {
            layout.setOnClickListener(this::onItemClicked)
        }

        private fun onItemClicked(view: View) {
            val articleUrl = savedArticlesList[layoutPosition].articleUrl
            ArticleReader.readArticle(articleUrl, context)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.saved_article_entry, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return savedArticlesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ctx: Context = holder.layout.context
        val article = savedArticlesList[position]

        Ion.with(holder.layout.image)
            .placeholder(R.drawable.white)
            .error(R.drawable.error)
            .load(article.imageUrl)
        holder.layout.article_title.text = article.title
        holder.layout.article_date.text = DateConverter.toDayMonthYearString(article.date)
        holder.layout.article_publisher.text = article.publisher

        holder.layout.remove.setOnClickListener {
            savedArticlesList.removeAt(position)
            this.notifyItemRemoved(position)
            this.notifyItemRangeChanged(position, itemCount)

            val snackBar = Snackbar.make(
                holder.layout.rootView.findViewById<CoordinatorLayout>(R.id.coordinator_layout),
                ctx.getString(R.string.article_removed),
                Snackbar.LENGTH_LONG
            ).setAction(ctx.getString(R.string.undo)) {
                savedArticlesList.add(position, article)
                this.notifyItemInserted(position)
            }
            snackBar.show()
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)

        val jsonSavedArticles = JsonSavedArticles(savedArticlesList)
        RWFile.writeToFile(savedArticlesFileName, jsonSavedArticles.toJsonArrayString(), context)
    }
}