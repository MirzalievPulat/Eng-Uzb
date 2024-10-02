package uz.gita.eng_uzb.domain

import android.database.Cursor
import uz.gita.eng_uzb.data.room.AppDatabase
import uz.gita.eng_uzb.data.room.entity.DictionaryEntity

class RepositoryImpl : Repository {
    private val dao = AppDatabase.getInstance().dictionaryDAO()
    private lateinit var historyCursor:Cursor

    companion object {
        private var repository: Repository? = null

        fun getInstance(): Repository {
            if (repository == null) {
                println("repository recreated")
                repository = RepositoryImpl()
            }

            return repository!!
        }
    }

    override fun getAllEngWords(): Cursor = dao.getAllEngDictionary()

    override fun getAllUzWords(): Cursor = dao.getAllUzDictionary()

    override fun updateWord(dictionaryEntity: DictionaryEntity) = dao.updateWord(dictionaryEntity)

    override fun searchByEnglishWord(query: String): Cursor = dao.searchByEnglish(query)

    override fun searchByUzbekWord(query: String): Cursor = dao.searchByUzbek(query)


    override fun getSavedWords(): Cursor = dao.getSavedWords()
    override fun getHistoryWords(): Cursor = dao.getHistoryWords().also { historyCursor = it }

    override fun deleteAll(){
        if (historyCursor.moveToFirst()){
            do {
                val id = historyCursor.getInt(historyCursor.getColumnIndexOrThrow("id"))
                dao.updateHistory(id,0)
            }while (historyCursor.moveToNext())
        }
        historyCursor.close()
    }


}