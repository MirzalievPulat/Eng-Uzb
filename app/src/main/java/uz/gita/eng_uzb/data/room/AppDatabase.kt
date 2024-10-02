package uz.gita.eng_uzb.data.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import uz.gita.eng_uzb.app.App
import uz.gita.eng_uzb.data.room.dao.DictionaryDAO
import uz.gita.eng_uzb.data.room.entity.DictionaryEntity

@Database(entities = [DictionaryEntity::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun dictionaryDAO(): DictionaryDAO

    companion object{
        private lateinit var instance: AppDatabase
//
//        val migration_1_to_2 = object : Migration(1,2){
//            override fun migrate(db: SupportSQLiteDatabase) {
//                db.execSQL("ALTER TABLE DICTIONARY ADD COLUMN history INTEGER NOT NULL DEFAULT 0")
//            }
//        }
//        val migration_2_to_3 = object : Migration(2, 3) {
//            override fun migrate(db: SupportSQLiteDatabase) {
//                db.execSQL("ALTER TABLE DICTIONARY ADD COLUMN time INTEGER NOT NULL DEFAULT 0")
//            }
//        }

        fun getInstance(): AppDatabase {
            if (!(Companion::instance.isInitialized)){
                println("room recreated")
                instance = Room.databaseBuilder(App.context, AppDatabase::class.java,"MyDatabase.db")
                    .createFromAsset("dictionary.db")
                    .allowMainThreadQueries()
//                    .addMigrations(migration_1_to_2, migration_2_to_3, migration_1_to_3)
//                    .fallbackToDestructiveMigration()
                    .build()
            }

            return instance
        }
    }


}