package com.example.ivanandreev.newsaggregator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val keyword = keywordList[position]
        holder.layout.keyword.text = keyword
        val ctx: Context = holder.layout.context

        holder.layout.remove.setOnClickListener {
            keywordList.removeAt(position)
            this.notifyItemRemoved(position)
            this.notifyItemRangeChanged(position, itemCount)

            val snackBar = Snackbar.make(
                holder.layout.rootView.findViewById(R.id.myCoordinatorLayout),
                "${ctx.getString(R.string.keyword_removed)} : \"$keyword\"!",
                Snackbar.LENGTH_LONG
            ).setAction(ctx.getString(R.string.undo)) {
                keywordList.add(position, keyword)
                notifyItemInserted(position)
            }
            snackBar.show()
        }
    }

}