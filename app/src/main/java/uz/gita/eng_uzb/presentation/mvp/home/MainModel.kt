package uz.gita.eng_uzb.presentation.mvp.home

import android.database.Cursor
import uz.gita.eng_uzb.data.room.entity.DictionaryEntity
import uz.gita.eng_uzb.domain.RepositoryImpl

class MainModel: MainContract.Model {
    private val repository = RepositoryImpl.getInstance()
    override fun getAllEngWords(): Cursor {
        return repository.getAllEngWords()
    }

    override fun getAllUzWords(): Cursor {
        return repository.getAllUzWords()
    }

    override fun updateWord(dictionaryEntity: DictionaryEntity) {
        repository.updateWord(dictionaryEntity)
    }

    override fun searchByEnglishWord(query: String): Cursor {
        return repository.searchByEnglishWord(query)
    }

    override fun searchByUzbekWord(query: String): Cursor {
        return repository.searchByUzbekWord(query)
    }
}