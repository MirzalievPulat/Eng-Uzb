package uz.gita.eng_uzb.presentation.mvp.history

import android.database.Cursor
import uz.gita.eng_uzb.data.room.entity.DictionaryEntity

interface HistoryContract {
    interface View{
        fun updateList(cursor: Cursor)
//        fun updateLang(isEng: Boolean)
    }
    interface Model{
        fun getHistoryWords(): Cursor
        fun updateWord(dictionaryEntity: DictionaryEntity)
        fun deleteAll()
    }
    interface Presenter{
        fun clickFavourite(dictionaryEntity: DictionaryEntity)
        fun updateList(): Cursor
        fun dialogOpened(dictionaryEntity: DictionaryEntity)
        fun deleteClick(dictionaryEntity: DictionaryEntity)
        fun deleteAll()
    }
}