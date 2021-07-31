package com.anwar.test.newslist.di

import com.anwar.test.newslist.data.NewsFilterSource
import com.anwar.test.newslist.data.NewsPagingSource
import com.anwar.test.newslist.data.NewsRepoImp
import com.anwar.test.newslist.domain.NewsSourceFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NewsSourceModule {
    @Provides
    internal fun provideMoviesSource(): NewsSourceFactory {
        return NewsSourceFactory()
    }

    @Provides
    @Singleton
    internal fun provideMoviesPagingSource(newsRepoImp: NewsRepoImp): NewsPagingSource {
        return NewsPagingSource(newsRepoImp)
    }

    @Provides
    @Singleton
    internal fun provideMoviesFilterSource(newsRepoImp: NewsRepoImp): NewsFilterSource {
        return NewsFilterSource(newsRepoImp)
    }
}