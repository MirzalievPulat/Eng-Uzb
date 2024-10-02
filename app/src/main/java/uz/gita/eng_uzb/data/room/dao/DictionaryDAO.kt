package uz.gita.eng_uzb.data.room.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import uz.gita.eng_uzb.data.room.entity.DictionaryEntity

@Dao
interface DictionaryDAO {

    //don't have to write like this:dictionary.uzbek just uzbek

    @Query("SELECT * FROM DICTIONARY")
    fun getAllEngDictionary():Cursor

    @Query("SELECT * FROM DICTIONARY ORDER BY dictionary.uzbek")
    fun getAllUzDictionary():Cursor

    @Query("SELECT * FROM DICTIONARY WHERE DICTIONARY.english LIKE :query || '%'")
    fun searchByEnglish(query:String):Cursor

    @Query("SELECT * FROM DICTIONARY WHERE DICTIONARY.uzbek LIKE :query || '%'")
    fun searchByUzbek(query:String):Cursor

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateWord(dictionaryEntity: DictionaryEntity)

    @Query("SELECT * FROM DICTIONARY WHERE DICTIONARY.is_favourite = 1")
    fun getSavedWords():Cursor

    @Query("SELECT * FROM DICTIONARY WHERE DICTIONARY.history = 1 ORDER BY time DESC")
    fun getHistoryWords():Cursor

    @Query("UPDATE DICTIONARY SET history = :historyValue WHERE id = :id")
    fun updateHistory(id: Int, historyValue: Int)
}