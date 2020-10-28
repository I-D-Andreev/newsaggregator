package com.example.ivanandreev.newsaggregator.fragments

import com.google.android.material.button.MaterialButton

class KeywordEntry {
     var keyword: String = ""
     var removeButton: MaterialButton? = null

    constructor(){}
    constructor(keyword_:String, removeButton_:MaterialButton){
        this.keyword = keyword_
        this.removeButton = removeButton_
    }
}