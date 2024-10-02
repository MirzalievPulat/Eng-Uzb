package uz.gita.eng_uzb.utils

import android.database.Cursor
import uz.gita.eng_uzb.data.room.entity.DictionaryEntity


fun Cursor.getWordData(): DictionaryEntity {
    return DictionaryEntity(
//        word id
        getInt(0),

//        english word
        getString(1),

//        word type
        getString(2),

//        word transcript
        getString(3),

//        word uzbek
        getString(4),

//        word countable
        getString(5),

//        word is_favourite
        getInt(6),

        //history
        getInt(7),

        //time
        getLong(8),
    )
}