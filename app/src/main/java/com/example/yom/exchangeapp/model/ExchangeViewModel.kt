package com.example.yom.exchangeapp.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.yom.exchangeapp.entity.ExchangeEntity
import com.example.yom.exchangeapp.repository.ExchangeRepository


class ExchangeViewModel(application: Application) : AndroidViewModel(application) {

    private var mRepository: ExchangeRepository = ExchangeRepository(application)
    internal var allList: LiveData<List<ExchangeEntity>>

    init {
        allList = mRepository.allList
        // countList=mRepository.countList
    }

    fun getItemCounts(code: String): Int {
        return mRepository.getCountItem(code)
    }

    fun insert(exchangeEntity: ExchangeEntity) {
        mRepository.insert(exchangeEntity)
    }
}