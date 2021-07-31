package com.anwar.test.detail.di

import androidx.recyclerview.widget.LinearLayoutManager
import com.anwar.test.detail.presentation.DetailFragment
import com.anwar.test.detail.presentation.CommentAdapter
import dagger.Module
import dagger.Provides


@Module
class DetailFragmentModule {

    @Provides
    internal fun provideLinearLayoutManager(fragment: DetailFragment): LinearLayoutManager {
        return LinearLayoutManager(fragment.activity)
    }

    @Provides
    internal fun provideTrailerAdapter(): CommentAdapter {
        return CommentAdapter(ArrayList())
    }

}