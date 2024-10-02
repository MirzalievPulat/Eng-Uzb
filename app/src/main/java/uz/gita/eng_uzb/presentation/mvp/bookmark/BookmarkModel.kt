package uz.gita.eng_uzb.presentation.mvp.bookmark

import android.database.Cursor
import uz.gita.eng_uzb.data.room.entity.DictionaryEntity
import uz.gita.eng_uzb.domain.RepositoryImpl
import uz.gita.eng_uzb.utils.getWordData

class BookmarkModel : BookmarkContract.Model {
    private val repository = RepositoryImpl.getInstance()

    override fun getSavedWords(): Cursor = repository.getSavedWords()

    override fun updateWord(dictionaryEntity: DictionaryEntity) {
        repository.updateWord(dictionaryEntity)
    }
}