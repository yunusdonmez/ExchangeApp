package com.xchyom.yom.exchangeapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.xchyom.yom.exchangeapp.entity.CrossValuesEntity
import com.xchyom.yom.exchangeapp.repository.CrossValuesRepository

class CrossValuesViewModel(application: Application) : AndroidViewModel(application) {
    private var mRepository: CrossValuesRepository = CrossValuesRepository(application)
    internal var allList: LiveData<List<CrossValuesEntity>>

    init {
        allList = mRepository.allList
        //Log.e("Cross","viewmodel init calisti")

    }

    fun insert(values: ArrayList<CrossValuesEntity>) {
        //Log.e("Cross","viewmodel insert calisti")
        mRepository.insert(values)
    }

    fun getTotalCount(): Int {
        return mRepository.getTotalCount()
    }
}
