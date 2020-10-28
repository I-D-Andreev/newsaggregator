package com.example.ivanandreev.newsaggregator.fragments

import com.google.android.material.button.MaterialButton

class KeywordEntry {
     var keyword: String = ""
     var exitButton: MaterialButton? = null

    constructor(){}
    constructor(keyword_:String, exitButton_:MaterialButton){
        this.keyword = keyword_
        this.exitButton = exitButton_
    }
}