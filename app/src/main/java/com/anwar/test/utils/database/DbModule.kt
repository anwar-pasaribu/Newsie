package com.anwar.test.utils.database

import com.anwar.test.utils.commons.AppConstants
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


@Module
class DbModule{

    @Provides
    @Singleton
    @Named(AppConstants.DB_NAME_KEY)
    internal fun provideMovieDb(context: Context): NewsDb {
        return Room.databaseBuilder(context, NewsDb::class.java, AppConstants.DB_NAME).fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    internal fun provideMovieDao(context: Context): NewsDao {
        return provideMovieDb(context).movieDao()
    }
}