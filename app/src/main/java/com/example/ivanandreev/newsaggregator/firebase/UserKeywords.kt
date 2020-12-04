package com.example.ivanandreev.newsaggregator.firebase

class UserKeywords(val keywords: MutableList<String>) {

    companion object {
        fun getKeywordsFieldName(): String {
            return "keywords"
        }
    }

}