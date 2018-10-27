package com.example.yom.exchangeapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "following")
class ExchangeEntity(
        @PrimaryKey val code: String,
        @ColumnInfo(name = "type") val moneyType: String,
        @ColumnInfo(name = "selling") val valueSelling: String,
        @ColumnInfo(name = "buying") val valueBuying: String,
        @ColumnInfo(name = "coFlag") val flag: String,
        @ColumnInfo(name = "follow") var isFollow: Boolean
)