package uz.gita.eng_uzb.presentation.mvp.history

import android.database.Cursor
import android.util.Log
import uz.gita.eng_uzb.data.room.entity.DictionaryEntity
import uz.gita.eng_uzb.presentation.mvp.bookmark.BookmarkContract
import uz.gita.eng_uzb.presentation.mvp.bookmark.BookmarkModel

class HistoryPresenter(private val view:HistoryContract.View):HistoryContract.Presenter {

    private val model: HistoryContract.Model = HistoryModel()

    init {
        view.updateList(updateList())
    }

    override fun clickFavourite(dictionaryEntity: DictionaryEntity) {
        model.updateWord(dictionaryEntity)
        view.updateList(updateList())
    }

    override fun updateList(): Cursor {
        return model.getHistoryWords()
    }

    override fun dialogOpened(dictionaryEntity: DictionaryEntity) {
        model.updateWord(dictionaryEntity.copy(history = 1, time = System.currentTimeMillis()))
    }

    override fun deleteClick(dictionaryEntity: DictionaryEntity) {
        model.updateWord(dictionaryEntity.copy(history = 0, time = 0))
        view.updateList(updateList())
    }

    override fun deleteAll() {
        model.deleteAll()
        view.updateList(updateList())
    }
}