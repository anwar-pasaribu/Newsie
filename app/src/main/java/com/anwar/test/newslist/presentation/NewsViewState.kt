package com.anwar.test.newslist.presentation

import com.anwar.test.newslist.data.News
import androidx.paging.PagingData


sealed class NewsViewState {
    class FetchingNewsError(val errorMessage: String?) : NewsViewState()
    class FetchingNewsSuccess(val movies: PagingData<News>) : NewsViewState()
}
