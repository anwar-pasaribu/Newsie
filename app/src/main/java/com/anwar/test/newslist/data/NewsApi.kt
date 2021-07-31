package com.anwar.test.newslist.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton


@Singleton
interface NewsApi {

    companion object {
        const val HN_TOP_STORIES: String = ("beststories.json")
        const val HN_ITEM: String = ("item/{id}.json")
    }


    @GET(HN_TOP_STORIES)
    suspend fun getTopStories() : Response<ArrayList<String>>

    @GET(HN_ITEM)
    suspend fun getItem(@Path("id") newsId: String) : Response<News>

}