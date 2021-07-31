package com.anwar.test.detail.di

import com.anwar.test.detail.di.DetailFragmentModule
import com.anwar.test.detail.presentation.DetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class DetailFragmentProvider {

    @ContributesAndroidInjector(modules =[(DetailFragmentModule::class)])
    internal abstract fun provideDetailFragmentFactory(): DetailFragment

}