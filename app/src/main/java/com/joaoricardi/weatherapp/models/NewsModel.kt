package com.joaoricardi.weatherapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class NewsModel (
    val author: String? = "" ,
    val title: String? = "",
    val description: String? = "",
    val content: String? = "",
    val urlToImage: String? = ""
): Parcelable

