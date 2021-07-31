package com.anwar.test.utils.di.module

import com.anwar.test.detail.data.CommentApi
import com.anwar.test.newslist.data.NewsApi
import com.anwar.test.newslist.data.NewsRepo
import com.anwar.test.newslist.data.NewsRepoImp
import com.anwar.test.utils.database.NewsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepoModule {

    @Provides
    @Singleton
    internal fun provideMovieRepository(newsDao: NewsDao,
                                        newsApi: NewsApi,
                                        commentApi: CommentApi
    ): NewsRepo {
        return NewsRepoImp(newsDao, newsApi, commentApi)
    }

}