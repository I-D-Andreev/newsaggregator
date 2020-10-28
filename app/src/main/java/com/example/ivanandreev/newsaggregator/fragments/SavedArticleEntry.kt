package com.example.ivanandreev.newsaggregator.fragments

import java.util.*


class SavedArticleEntry {
    var title: String = ""
    var publisher: String = ""
    var image : Int = 0
    var date : Calendar = Calendar.getInstance()
//    var removeButton: MaterialButton? = null
//    summary
//    http link
//    date

    constructor(){}
    constructor(title_: String, publisher_ : String, image_: Int, date_: Calendar){
        this.title = title_
        this.publisher = publisher_
        this.image = image_
        this.date = date_
    }

}