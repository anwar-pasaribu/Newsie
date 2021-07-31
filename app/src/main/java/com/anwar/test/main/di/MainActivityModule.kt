package com.anwar.test.main.di

import com.anwar.test.utils.commons.ViewModelProviderFactory
import com.anwar.test.newslist.presentation.NewsViewModel
import com.anwar.test.newslist.data.NewsRepoImp
import com.anwar.test.newslist.domain.NewsSourceFactory
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    internal fun provideMoviesViewModel(newsRepoImp: NewsRepoImp, sourceFactory:NewsSourceFactory): NewsViewModel {
        return NewsViewModel(newsRepoImp,sourceFactory)
    }

    @Provides
    internal fun provideMoviesViewModelFactory(newsViewModel: NewsViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(newsViewModel)
    }

}