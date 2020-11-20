package com.example.ivanandreev.newsaggregator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.keyword_entry.view.*
import com.example.ivanandreev.newsaggregator.*
import com.example.ivanandreev.newsaggregator.fragments.KeywordEntry
import com.google.android.material.snackbar.Snackbar


class KeywordAdapter(val keywordList: MutableList<KeywordEntry>) :
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
        val data = keywordList[position]
        holder.layout.keyword.text = data.keyword
        holder.layout.remove.setOnClickListener {
            keywordList.removeAt(position)
            this.notifyItemRemoved(position)
            val snackBar = Snackbar.make(holder.layout.rootView.findViewById(R.id.myCoordinatorLayout), "Keyword \"${data.keyword}\" removed!", Snackbar.LENGTH_LONG)
            snackBar.show()
        }
    }

}