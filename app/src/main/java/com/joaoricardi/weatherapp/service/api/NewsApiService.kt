package com.joaoricardi.weatherapp.service.api

import com.joaoricardi.weatherapp.models.NewsModel
import com.joaoricardi.weatherapp.models.NewsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
   suspend fun getNews(
        @Query("page")page: Int,
        @Header("Authorization")token: String,
        @Query("country")contry: String
    ): Response<NewsResponse>
}