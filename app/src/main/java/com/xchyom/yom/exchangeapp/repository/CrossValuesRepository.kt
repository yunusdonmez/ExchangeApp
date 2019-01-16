package com.xchyom.yom.exchangeapp.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.xchyom.yom.exchangeapp.dao.CrossValuesDao
import com.xchyom.yom.exchangeapp.db.ExchangeDB
import com.xchyom.yom.exchangeapp.entity.CrossValuesEntity

class CrossValuesRepository internal constructor(application: Application) {
    private var crossValuesDao: CrossValuesDao
    var allList: LiveData<List<CrossValuesEntity>>

    init {
        val db = ExchangeDB.getInstance(application)
        crossValuesDao = db!!.crossDao()
        allList = crossValuesDao.getAll()
        //Log.e("Cross","Toplam Sayi $count")
    }

    fun insert(valuesList: ArrayList<CrossValuesEntity>) {
        //Log.e("Cross","inset fonk calisti")
        InsertCrossAsyncTask(crossValuesDao).execute(valuesList)
    }

    fun getTotalCount(): Int = GetCountCrossAsyncTask(crossValuesDao).execute().get()

}

private class InsertCrossAsyncTask internal constructor(private val mAsyncTaskDao: CrossValuesDao) : AsyncTask<ArrayList<CrossValuesEntity>, Void, Void>() {
    override fun doInBackground(vararg params: ArrayList<CrossValuesEntity>): Void? {
        //Log.e("Cross", "async fonk calisti")
        mAsyncTaskDao.insertAll(params[0])
        return null
    }
}

private class GetCountCrossAsyncTask internal constructor(private val mAsyncTaskDao: CrossValuesDao) : AsyncTask<Void, Void, Int>() {
    override fun doInBackground(vararg params: Void?): Int {
        //Log.e("Cross", "${mAsyncTaskDao.getCounts()}")
        return mAsyncTaskDao.getCounts()
    }
}

