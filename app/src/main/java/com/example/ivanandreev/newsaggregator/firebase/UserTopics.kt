package com.example.ivanandreev.newsaggregator.firebase

import android.content.Context
import com.example.ivanandreev.newsaggregator.R
import com.google.gson.JsonObject

class UserTopics(context: Context) {

    val topics: HashMap<String, Boolean> = HashMap()
    // to keep the order of entries
    val topicsList: Array<String> = context.resources.getStringArray(R.array.topics_list)
    init {

        for (topic: String in topicsList){
            topics[topic] = false
        }
    }

    fun updateTopic(topic: String, isSelected:Boolean){
        if(topics.containsKey(topic)){
            topics[topic] = isSelected
        }
    }

    companion object {
        fun from(context: Context, json: JsonObject): UserTopics{
            return UserTopics(context)
        }
    }
}
