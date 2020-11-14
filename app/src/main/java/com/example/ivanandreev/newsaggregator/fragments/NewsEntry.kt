package com.example.ivanandreev.newsaggregator.fragments

import java.time.ZonedDateTime
import java.util.*


class NewsEntry {
    var title: String = ""
    var publisher: String = ""
//    var author: String = ""
    var imageUrl : String = ""
    var date : Calendar = Calendar.getInstance()
//    var removeButton: MaterialButton? = null
//    summary
//    http link
//    date

    constructor(){}
    constructor(title_: String/*, author_: String*/, publisher_ : String, imageUrl_: String, date_: Calendar){
        this.title = title_
//        this.author = author_
        this.publisher = publisher_
        this.imageUrl = imageUrl_
        this.date = date_
    }

}