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


class NewsArticleAdapter(private val newsArticlesList: MutableList<NewsEntry>) :
    RecyclerView.Adapter<NewsArticleAdapter.ViewHolder>() {

    inner class ViewHolder(val layout: View) : RecyclerView.ViewHolder(layout) {
        init {
            layout.setOnClickListener(this::onItemClicked)
        }

        private fun onItemClicked(view: View) {
            val ctx: Context = view.context
            val actions: Array<String> = ctx.resources.getStringArray(R.array.news_article_actions)
            val articlePosition: Int = this.layoutPosition

            val dialog: AlertDialog = AlertDialog.Builder(view.context)
                .setTitle(ctx.getString(R.string.choose_action))
                .setItems(actions) { _: DialogInterface?, which: Int ->
                    when (which) {
                        0 -> visitArticle(ctx, articlePosition)
                        1 -> saveArticle(ctx, articlePosition)
                    }
                }
                .setNegativeButton(ctx.getString(R.string.cancel), null)
                .create()
            dialog.show()
        }
    }

    private fun saveArticle(ctx: Context, position: Int) {
        // todo1: initialize jsonarticles at top and then save only once on view exit
        // this way we can also check that we can't save the same article twice
        println("!!! Save article")
        val fileName = ctx.getString(R.string.saved_articles_file)
        val article: NewsEntry = newsArticlesList[position]

        val currentData: String = RWFile.readFromFile(fileName, ctx)

        val currentSavedArticles: JsonSavedArticles =
            if (currentData.isNotEmpty()) JsonSavedArticles(currentData) else JsonSavedArticles()

        currentSavedArticles.addArticle(article)
        RWFile.writeToFile(fileName, currentSavedArticles.toJsonArray(), ctx)
    }

    private fun visitArticle(ctx: Context, position: Int) {
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