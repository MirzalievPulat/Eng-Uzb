package uz.gita.eng_uzb.presentation.mvp.home

import android.database.Cursor
import uz.gita.eng_uzb.data.room.entity.DictionaryEntity

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {
    private val model: MainContract.Model = MainModel()
    private lateinit var lastQuery:String
    var isEng = true
    init {
        view.updateList(getWordsByLang())
    }

    override fun clickFavourite(dictionaryEntity: DictionaryEntity) {
        model.updateWord(dictionaryEntity)
        searchByQuery(lastQuery)
    }

    override fun dialogOpened(dictionaryEntity: DictionaryEntity) {
        model.updateWord(dictionaryEntity.copy(history = 1, time = System.currentTimeMillis()))
    }

    override fun searchByQuery(query: String) {
        lastQuery = query
        if (isEng) {
            view.updateList(model.searchByEnglishWord(query))
        } else {
            view.updateList(model.searchByUzbekWord(query))
        }
    }

    override fun clickLan() {
        isEng = !isEng
        view.updateList(getWordsByLang())
        view.updateLang(isEng)
    }

    override fun getWordsByLang(): Cursor {
        return if (isEng) model.getAllEngWords() else model.getAllUzWords()
    }
}