package com.anwar.test.newslist.di

import com.anwar.test.newslist.presentation.NewsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NewsFragmentProvider {

    @ContributesAndroidInjector(modules =[(NewsFragmentModule::class),(NewsSourceModule::class),])
    internal abstract fun provideMainFragmentFactory(): NewsListFragment

}