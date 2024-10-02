package uz.gita.eng_uzb.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary")
data class DictionaryEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val english: String?,
    val type : String?,
    val transcript :String?,
    val uzbek : String?,
    val countable: String?,
    @ColumnInfo("is_favourite")
    val isFavourite: Int?,
    val history:Int,
    val time:Long
)