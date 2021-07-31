package com.anwar.test.newslist.domain

import com.anwar.test.newslist.data.News
import com.anwar.test.newslist.data.NewsFilterSource
import com.anwar.test.newslist.data.NewsPagingSource
import androidx.paging.PagingSource
import javax.inject.Inject

class NewsSourceFactory @Inject constructor() {

    @Inject
    lateinit var newsFilterSource: NewsFilterSource

    @Inject
    lateinit var newsPagingSource: NewsPagingSource

    fun getSource( filterText: String): PagingSource<Int, News> {
        return if (filterText.isBlank() || filterText.isEmpty()) newsPagingSource
        else newsFilterSource.apply { this.filterText = filterText }
    }
}
