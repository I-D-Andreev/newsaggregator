package com.example.ivanandreev.newsaggregator.firebase

import android.content.Context
import com.example.ivanandreev.newsaggregator.R

class UserTopics {

    var topics: HashMap<String, Boolean> = HashMap()

    // to keep the order of entries
    var topicsList: Array<String>

    constructor(context: Context) {
        topicsList = topicsList(context)
        for (topic: String in topicsList) {
            topics[topic] = false
        }
    }

    constructor(context: Context, topics_: MutableMap<String, Any>?) {
        topicsList = topicsList(context)
        topics = topics_ as HashMap<String, Boolean>

    }

    fun updateTopic(topic: String, isSelected: Boolean) {
        if (topics.containsKey(topic)) {
            topics[topic] = isSelected
        }
    }

    fun isChecked(topic: String): Boolean? {
        return topics[topic]
    }

    companion object{
        fun topicsList(context: Context): Array<String>{
            return context.resources.getStringArray(R.array.topics_list)
        }
    }
}
