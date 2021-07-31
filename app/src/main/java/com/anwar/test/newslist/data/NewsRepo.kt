package com.anwar.test.newslist.data

import com.anwar.test.utils.network.ResultType

interface NewsRepo {
    suspend fun getNews(newsId: String): ResultType<News>
    suspend fun getPopularMovies(page: Int): ResultType<ArrayList<String>>
    suspend fun getFilteredPopularMovies(filterText: String): NewsResponse?
    fun isMovieLiked(id: Int): Boolean
    fun changeLikeState(news: News, newLikeState: Boolean)
}