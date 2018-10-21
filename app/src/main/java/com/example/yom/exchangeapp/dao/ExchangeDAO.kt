package com.example.yom.exchangeapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.yom.exchangeapp.entity.ExchangeEntity


@Dao
interface ExchangeDAO {
    @Query("SELECT * FROM following")
    fun getAll(): LiveData<List<ExchangeEntity>>

    @Query("SELECT count(*) FROM following WHERE code IN (:countryCode)")
    fun getItemCounts(countryCode: String): Int

    @Query("SELECT count(*) FROM following")
    fun getCounts(): Int

    @Insert(onConflict = REPLACE)
    fun insertItem(moneyList: ExchangeEntity)

    @Delete
    fun delete(moneyList: ExchangeEntity)
}