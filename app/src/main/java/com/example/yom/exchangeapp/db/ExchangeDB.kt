package com.example.yom.exchangeapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.yom.exchangeapp.dao.ExchangeDAO
import com.example.yom.exchangeapp.entity.ExchangeEntity

@Database(entities = [ExchangeEntity::class], version = 2)
abstract class ExchangeDB : RoomDatabase() {
    abstract fun exchDao(): ExchangeDAO

    companion object {
        private var INSTANCE: ExchangeDB? = null

        fun getInstance(context: Context): ExchangeDB? {
            if (INSTANCE == null) {
                synchronized(ExchangeDB::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            ExchangeDB::class.java, "exchangeDB")
                            .fallbackToDestructiveMigration()
//                            .addCallback(object : RoomDatabase.Callback() {
//
//                                override fun onOpen(db: SupportSQLiteDatabase) {
//                                    super.onOpen(db)
//                                    PopulateDbAsync(INSTANCE!!).execute()
//                                }
//                            })
                            .build()
                }
            }
            return INSTANCE
        }
    }

}