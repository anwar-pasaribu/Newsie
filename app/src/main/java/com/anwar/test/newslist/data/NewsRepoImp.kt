package com.anwar.test.newslist.data

import com.anwar.test.detail.data.CommentApi
import com.anwar.test.utils.database.NewsDao
import com.anwar.test.utils.network.NetworkRouter
import com.anwar.test.utils.network.ResultType
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewsRepoImp @Inject constructor(
    private val newsDao: NewsDao,
    private val newsApi: NewsApi,
    private val commentApi: CommentApi
) : NewsRepo {

    private var moviesResponse: NewsResponse? = null

    override suspend fun getPopularMovies(page: Int): ResultType<ArrayList<String>> {
        return NetworkRouter.invokeCall {
            newsApi.getTopStories()
        }
    }

    override suspend fun getNews(newsId: String): ResultType<News> {
        val newsList = arrayListOf<News>()
        val responseNewsItem = NetworkRouter.invokeCall {
            newsApi.getItem(newsId)
        }

        if (responseNewsItem is ResultType.Success) {
            newsList.add(responseNewsItem.data)
        }

        return responseNewsItem
    }

    override suspend fun getFilteredPopularMovies(filterText: String): NewsResponse? {
        val result = moviesResponse?.results?.filter { movie -> movie.title?.contains(filterText, true) == true }?.toList()
        result?.let {
            val response = moviesResponse?.copy()
            response?.results = ArrayList(it)
            return response
        }
        return null
    }

    override fun isMovieLiked(id: Int): Boolean {
        return newsDao.fetchFavouriteMovies().contains(id)
    }

    override fun changeLikeState(news: News, newLikeState: Boolean) {
        if (newLikeState) newsDao.insertMovie(news)
        else newsDao.removeMovie(news)
    }

}