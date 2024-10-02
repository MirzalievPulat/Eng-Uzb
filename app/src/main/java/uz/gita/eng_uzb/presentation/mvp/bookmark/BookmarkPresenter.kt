package uz.gita.eng_uzb.presentation.mvp.bookmark

import android.database.Cursor
import android.util.Log
import uz.gita.eng_uzb.data.room.entity.DictionaryEntity

class BookmarkPresenter(private val view: BookmarkContract.View):BookmarkContract.Presenter {
    private val model:BookmarkContract.Model = BookmarkModel()

    init {
        Log.d("TTT", "dnanjd: ${model.getSavedWords().count}")
        view.updateList(updateList())
    }

    override fun clickFavourite(dictionaryEntity: DictionaryEntity) {
        model.updateWord(dictionaryEntity)
        view.updateList(updateList())
    }

    override fun updateList(): Cursor {
        return model.getSavedWords()
    }

    override fun dialogOpened(dictionaryEntity: DictionaryEntity) {
        model.updateWord(dictionaryEntity.copy(history = 1, time = System.currentTimeMillis()))
    }

}