package com.anwar.test.utils.di.builder

import com.anwar.test.detail.di.DetailFragmentProvider
import com.anwar.test.main.di.MainActivityModule
import com.anwar.test.newslist.di.NewsFragmentProvider
import com.anwar.test.main.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {


    @ContributesAndroidInjector(modules = [(MainActivityModule::class), (NewsFragmentProvider::class), (DetailFragmentProvider::class)])
    internal abstract fun bindMainActivity(): MainActivity

}