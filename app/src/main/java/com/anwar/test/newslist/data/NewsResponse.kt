package com.anwar.test.newslist.data


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class NewsResponse(
        var page: Int? = 0,
        var total_results: Int? = 0,
        var total_pages: Int,
        var results: ArrayList<News>) : Parcelable

