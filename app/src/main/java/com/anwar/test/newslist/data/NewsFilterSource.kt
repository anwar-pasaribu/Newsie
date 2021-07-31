package com.anwar.test.newslist.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import javax.inject.Inject

class NewsFilterSource @Inject constructor(private val repo: NewsRepo) : PagingSource<Int, News>() {

    var filterText:String? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        return if (filterText == null) LoadResult.Error(Exception(""))
        else try {
            val movieListResponse = repo.getFilteredPopularMovies(filterText!!)!!
            LoadResult.Page(
                    data = movieListResponse.results,
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