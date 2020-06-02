package com.joaoricardi.weatherapp.models

class NewsModel (
    val source: SourceModel,
    val author: String? = "" ,
    val title: String? = "",
    val description: String? = "",
    val content: String? = ""
)

