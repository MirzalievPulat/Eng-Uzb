package uz.gita.eng_uzb.domain

import android.database.Cursor
import uz.gita.eng_uzb.data.room.entity.DictionaryEntity

interface Repository {
    fun getAllEngWords(): Cursor
    fun getAllUzWords(): Cursor
    fun updateWord(dictionaryEntity: DictionaryEntity)
    fun searchByEnglishWord(query: String): Cursor
    fun searchByUzbekWord(query: String): Cursor


    fun getSavedWords():Cursor
    fun getHistoryWords():Cursor
    fun deleteAll()
}