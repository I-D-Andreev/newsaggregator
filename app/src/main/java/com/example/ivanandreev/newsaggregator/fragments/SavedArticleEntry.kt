package com.example.ivanandreev.newsaggregator.fragments

class SavedArticleEntry {
    var title: String = ""
    var publisher: String = ""
    var image : Int = 0
//    var removeButton: MaterialButton? = null
//    summary
//    http link

    constructor(){}
    constructor(title_: String, publisher_ : String, image_: Int){
        this.title = title_
        this.publisher = publisher_
        this.image = image_
    }

}