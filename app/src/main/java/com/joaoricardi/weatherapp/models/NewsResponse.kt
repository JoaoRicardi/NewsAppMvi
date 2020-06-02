package com.joaoricardi.weatherapp.models

class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsModel>
)