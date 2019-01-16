package com.xchyom.yom.exchangeapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cross_values")
class CrossValuesEntity(

        @PrimaryKey val code: String,
        @ColumnInfo(name = "type") val moneyType: String,
        @ColumnInfo(name = "selling") val valueSelling: String,
        @ColumnInfo(name = "buying") val valueBuying: String
)