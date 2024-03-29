package com.example.ivanandreev.newsaggregator.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ivanandreev.newsaggregator.R
import com.example.ivanandreev.newsaggregator.adapters.KeywordAdapter
import com.example.ivanandreev.newsaggregator.firebase.FireDB
import com.example.ivanandreev.newsaggregator.firebase.UserKeywords
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot

class AccountKeywordsFragment : Fragment() {
    private val userEmail: String? = FirebaseAuth.getInstance().currentUser?.email
    private val db: FireDB = FireDB(FireDB.USER_KEYWORDS)
    private lateinit var loadedView: View
    private val logTag = AccountKeywordsFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.account_keywords_fragment, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadedView = view
        loadData()
        loadedView.findViewById<FloatingActionButton>(R.id.addKeyword)
            .setOnClickListener(this::showAddKeywordDialog)
    }

    private fun showAddKeywordDialog(view: View) {
        val textBox = EditText(loadedView.context)
        val dialog: AlertDialog = AlertDialog.Builder(loadedView.context)
            .setTitle(getString(R.string.add_keyword))
            .setView(textBox)
            .setPositiveButton(getString(R.string.add)) { _: DialogInterface, _: Int ->
                val keyword: String = textBox.text.toString()
                addKeyword(keyword)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .create()
        dialog.show()

    }

    private fun addKeyword(keyword: String) {
        val rv: RecyclerView = loadedView.findViewById<RecyclerView>(R.id.keywords_recyclerview)
        val adapter: KeywordAdapter = rv.adapter as KeywordAdapter

        val message = if (adapter.keywordList.contains(keyword)) {
            getString(R.string.keyword_exits)
        } else {
            adapter.keywordList.add(keyword)
            adapter.notifyItemInserted(adapter.itemCount - 1)
            getString(R.string.keyword_added)
        }

        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 200)
        toast.show()
    }

    private fun loadData() {
        val keywords = ArrayList<String>()

        if (userEmail != null) {
            db.getData(userEmail) { doc: DocumentSnapshot? ->
                if (doc != null && doc.data != null) {
                    for (keyword: String in doc.data!![UserKeywords::keywords.name] as ArrayList<String>) {
                        keywords.add(keyword)
                    }
                    Log.i(logTag, "Successfully loaded Keywords from DB!")
                } else {
                    Log.i(logTag, "Document data is empty!")
                }

                loadRecyclerView(keywords)
            }
        }

    }

    private fun loadRecyclerView(keywordsList: ArrayList<String>) {
        val recyclerView = loadedView.findViewById<RecyclerView>(R.id.keywords_recyclerview)
        val layoutManager = GridLayoutManager(loadedView.context, 2)
        val recyclerAdapter = KeywordAdapter(keywordsList)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerAdapter
    }

    override fun onStop() {
        super.onStop()

        if (userEmail != null) {
            val recyclerViewAdapter: KeywordAdapter =
                loadedView.findViewById<RecyclerView>(R.id.keywords_recyclerview).adapter as KeywordAdapter

            // need to wrap in POJO before saving to DB
            db.save(userEmail, UserKeywords(recyclerViewAdapter.keywordList))

            Log.i(logTag, "Keywords saved!")

        }
    }
}
