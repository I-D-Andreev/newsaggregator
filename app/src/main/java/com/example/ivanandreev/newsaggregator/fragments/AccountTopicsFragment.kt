package com.example.ivanandreev.newsaggregator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import com.example.ivanandreev.newsaggregator.R
import com.example.ivanandreev.newsaggregator.firebase.FireDB
import com.example.ivanandreev.newsaggregator.firebase.UserTopics
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot

class AccountTopicsFragment : Fragment() {
    private val userEmail: String? = FirebaseAuth.getInstance().currentUser?.email
    private val db: FireDB = FireDB("userTopics")
    private lateinit var userTopics: UserTopics
    private lateinit var loadedView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.account_topics_fragment, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadedView = view
        loadGridView()
    }


    private fun loadGridView() {
        val grid = loadedView.findViewById<GridLayout>(R.id.news_type_grid)
        userTopics = UserTopics(loadedView.context)

        for (i in 0 until grid.childCount) {
            (grid.getChildAt(i) as MaterialCheckBox).text = userTopics.topicsList[i]
        }

        if (userEmail != null) {
            db.getData(userEmail) { doc: DocumentSnapshot? ->
                if (doc != null) {
                    userTopics = UserTopics(loadedView.context, doc.data)

                    for (i in 0 until grid.childCount) {
                        (grid.getChildAt(i) as MaterialCheckBox).isChecked =
                            (userTopics.isChecked(userTopics.topicsList[i]) == true)
                    }
                    println("!!! Successfully loaded from DB!")
                }
            }
        }
    }


    override fun onStop() {
        super.onStop()
        println("!!! On Stop")

        if (userEmail != null) {
            val grid = loadedView.findViewById<GridLayout>(R.id.news_type_grid)
            for (i in 0 until grid.childCount) {
                val gridElem = grid.getChildAt(i) as MaterialCheckBox
                userTopics.updateTopic(gridElem.text.toString(), gridElem.isChecked)
            }
            db.save(userEmail, userTopics.topics)
        }
    }

}
