package uz.gita.eng_uzb.presentation.mvp.bookmark

import android.database.Cursor
import uz.gita.eng_uzb.data.room.entity.DictionaryEntity

interface BookmarkContract {

    interface View{
        fun updateList(cursor: Cursor)
//        fun updateLang(isEng: Boolean)
    }
    interface Model{
        fun getSavedWords(): Cursor
        fun updateWord(dictionaryEntity: DictionaryEntity)
    }
    interface Presenter{
        fun clickFavourite(dictionaryEntity: DictionaryEntity)
        fun updateList():Cursor
        fun dialogOpened(dictionaryEntity: DictionaryEntity)
    }

}