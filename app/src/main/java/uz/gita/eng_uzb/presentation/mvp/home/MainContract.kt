package uz.gita.eng_uzb.presentation.mvp.home

import android.database.Cursor
import uz.gita.eng_uzb.data.room.entity.DictionaryEntity

interface MainContract {
    interface View{
        fun updateList(cursor: Cursor)
        fun updateLang(isEng: Boolean)
    }
    interface Model{
        fun getAllEngWords(): Cursor
        fun getAllUzWords(): Cursor
        fun updateWord(dictionaryEntity: DictionaryEntity)
        fun searchByEnglishWord(query: String): Cursor
        fun searchByUzbekWord(query: String): Cursor
    }
    interface Presenter{
        fun clickFavourite(dictionaryEntity: DictionaryEntity)
        fun dialogOpened(dictionaryEntity: DictionaryEntity)
        fun searchByQuery(query: String)
        fun clickLan()
        fun getWordsByLang():Cursor
    }
}