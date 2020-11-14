package com.example.ivanandreev.newsaggregator.fragments

import java.time.ZonedDateTime
import java.util.*


class NewsEntry {
    var title: String = ""
    var publisher: String = ""
//    var author: String = ""
    var image : Int = 0
    var date : Calendar = Calendar.getInstance()
//    var removeButton: MaterialButton? = null
//    summary
//    http link
//    date

    constructor(){}
    constructor(title_: String/*, author_: String*/, publisher_ : String, image_: Int, date_: Calendar){
        this.title = title_
//        this.author = author_
        this.publisher = publisher_
        this.image = image_
        this.date = date_
    }

}