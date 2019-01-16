package com.xchyom.yom.exchangeapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.xchyom.yom.exchangeapp.dao.CrossValuesDao
import com.xchyom.yom.exchangeapp.dao.ExchangeDAO
import com.xchyom.yom.exchangeapp.entity.CrossValuesEntity
import com.xchyom.yom.exchangeapp.entity.ExchangeEntity

@Database(entities = [ExchangeEntity::class, CrossValuesEntity::class], version = 4, exportSchema = false)
abstract class ExchangeDB : RoomDatabase() {
    abstract fun exchDao(): ExchangeDAO
    abstract fun crossDao(): CrossValuesDao

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