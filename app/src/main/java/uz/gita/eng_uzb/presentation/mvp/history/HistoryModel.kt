package uz.gita.eng_uzb.presentation.mvp.history

import android.database.Cursor
import uz.gita.eng_uzb.data.room.entity.DictionaryEntity
import uz.gita.eng_uzb.domain.RepositoryImpl

class HistoryModel :HistoryContract.Model{

    private val repository = RepositoryImpl.getInstance()

    override fun getHistoryWords(): Cursor = repository.getHistoryWords()

    override fun updateWord(dictionaryEntity: DictionaryEntity) {
        repository.updateWord(dictionaryEntity)
    }

    override fun deleteAll()  = repository.deleteAll()
}