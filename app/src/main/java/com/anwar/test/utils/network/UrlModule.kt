package com.anwar.test.utils.network

import com.anwar.test.BuildConfig
import com.anwar.test.utils.commons.AppConstants.Companion.BASE_URL_KEY
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class UrlModule {
    @Provides
    @Singleton
    @Named(BASE_URL_KEY)
    fun provideBaseUrl(): String {
        return BuildConfig.BASE_URL
    }
}