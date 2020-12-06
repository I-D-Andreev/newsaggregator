package com.example.ivanandreev.newsaggregator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.ivanandreev.newsaggregator.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.keyword_entry.view.*


class KeywordAdapter(val keywordList: MutableList<String>) :
    RecyclerView.Adapter<KeywordAdapter.ViewHolder>() {

    inner class ViewHolder(var layout: View) : RecyclerView.ViewHolder(layout) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.keyword_entry, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return keywordList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ctx: Context = holder.layout.context
        val keyword = keywordList[position]
        holder.layout.keyword.text = keyword

        holder.layout.remove.setOnClickListener {
            keywordList.removeAt(position)
            this.notifyItemRemoved(position)
            this.notifyItemRangeChanged(position, itemCount)

            val snackBar = Snackbar.make(
                holder.layout.rootView.findViewById<CoordinatorLayout>(R.id.coordinator_layout),
                "${ctx.getString(R.string.keyword_removed)} : \"$keyword\"!",
                Snackbar.LENGTH_LONG
            ).setAction(ctx.getString(R.string.undo)) {
                keywordList.add(position, keyword)
                this.notifyItemInserted(position)
                this.notifyItemRangeChanged(position, itemCount)
            }
            snackBar.show()
        }
    }

}