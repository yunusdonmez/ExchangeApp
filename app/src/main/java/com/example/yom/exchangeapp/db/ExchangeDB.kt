package com.example.yom.exchangeapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yom.exchangeapp.dao.ExchangeDAO
import com.example.yom.exchangeapp.entity.ExchangeEntity

@Database(entities = [ExchangeEntity::class], version = 2)
abstract class ExchangeDB : RoomDatabase() {
    abstract fun exchDao(): ExchangeDAO

}