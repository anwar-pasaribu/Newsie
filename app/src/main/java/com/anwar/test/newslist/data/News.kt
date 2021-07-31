package com.anwar.test.newslist.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class News(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "score") var score: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "text") val text: String?,
    @ColumnInfo(name = "kids") val kids: ArrayList<String>
) : Parcelable {
    class Converters {
        @TypeConverter
        fun toListOfStrings(flatStringList: String): List<String> {
            return flatStringList.split(",")
        }

        @TypeConverter
        fun fromListOfStrings(listOfString: List<String>): String {
            return listOfString.joinToString(",")
        }
    }
}