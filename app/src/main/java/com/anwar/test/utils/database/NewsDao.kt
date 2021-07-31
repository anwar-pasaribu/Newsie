package com.anwar.test.utils.database

import androidx.room.*
import com.anwar.test.newslist.data.News

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(news: News)

    @Query("SELECT id FROM movies")
    fun fetchFavouriteMovies(): List<Int?>

    @Delete()
    fun removeMovie(news: News)

}