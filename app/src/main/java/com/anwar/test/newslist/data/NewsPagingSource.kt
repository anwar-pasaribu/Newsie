package com.anwar.test.newslist.data

import com.anwar.test.utils.network.ResultType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import javax.inject.Inject

class NewsPagingSource @Inject constructor(private val repo: NewsRepo) : PagingSource<Int, News>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        return try {
            val nextPage = params.key ?: 1
            val newsIdListResponse = (repo.getPopularMovies(nextPage) as ResultType.Success).data
            val newsList = newsIdListResponse.subList(0, 10).map { newsId ->
                (repo.getNews(newsId) as ResultType.Success).data
            }
            LoadResult.Page(
                    data = newsList,
                    prevKey = null,
                    nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return null
    }
}