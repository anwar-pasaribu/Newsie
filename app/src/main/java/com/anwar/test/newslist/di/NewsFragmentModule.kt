package com.anwar.test.newslist.di

import com.anwar.test.utils.commons.GridSpacingItemDecoration
import com.anwar.test.newslist.presentation.NewsAdapter
import com.anwar.test.newslist.presentation.NewsListFragment
import androidx.recyclerview.widget.GridLayoutManager
import dagger.Module
import dagger.Provides


@Module
class NewsFragmentModule {

    @Provides
    internal fun provideGridLayoutManager(fragment: NewsListFragment): GridLayoutManager {
        return GridLayoutManager(fragment.requireContext(), 2)
    }

    @Provides
    internal fun provideGridSpacingItemDecoration(): GridSpacingItemDecoration {
        return GridSpacingItemDecoration(2, 5, true)
    }

    @Provides
    internal fun provideMovieAdapter(): NewsAdapter {
        return NewsAdapter()
    }

}