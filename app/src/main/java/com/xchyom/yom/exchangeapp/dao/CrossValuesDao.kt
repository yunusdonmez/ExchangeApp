package com.xchyom.yom.exchangeapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xchyom.yom.exchangeapp.entity.CrossValuesEntity

@Dao
interface CrossValuesDao {
    @Query("SELECT * FROM cross_values")
    fun getAll(): LiveData<List<CrossValuesEntity>>

    @Query("SELECT count(*) FROM cross_values")
    fun getCounts(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(values: ArrayList<CrossValuesEntity>)
}