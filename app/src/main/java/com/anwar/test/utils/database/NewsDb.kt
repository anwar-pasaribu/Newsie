package com.anwar.test.utils.database

import androidx.room.*
import com.anwar.test.newslist.data.News
import javax.inject.Singleton


@Singleton
@Database(entities = [(News::class)], version = 1, exportSchema = false)
@TypeConverters(News.Converters::class)
abstract class NewsDb : RoomDatabase() {
    abstract fun movieDao(): NewsDao
}


