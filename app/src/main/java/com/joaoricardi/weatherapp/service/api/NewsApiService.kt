package com.joaoricardi.weatherapp.service.api

import com.joaoricardi.weatherapp.models.NewsModel
import com.joaoricardi.weatherapp.models.NewsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    fun getNews(
        @Header("Authorization")token: String,
        @Query("country")contry: String,
        @Path("page")page: Int
    ): Deferred<NewsResponse>
}