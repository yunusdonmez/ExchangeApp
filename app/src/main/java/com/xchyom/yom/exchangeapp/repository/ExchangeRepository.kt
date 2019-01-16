package com.xchyom.yom.exchangeapp.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.xchyom.yom.exchangeapp.dao.ExchangeDAO
import com.xchyom.yom.exchangeapp.db.ExchangeDB
import com.xchyom.yom.exchangeapp.entity.ExchangeEntity

class ExchangeRepository internal constructor(application: Application) {
    private var exchangeDAO: ExchangeDAO
    var allList: LiveData<List<ExchangeEntity>>

    init {
        val db = ExchangeDB.getInstance(application)
        exchangeDAO = db!!.exchDao()
        allList = exchangeDAO.getAll()
    }

    fun insert(moneyList: ExchangeEntity) {
        InsertAsyncTask(exchangeDAO).execute(moneyList)
    }

    fun updateDatas() {
        allList = exchangeDAO.getAll()
    }

    fun getCountItem(code: String): Int = SelectAsyncTask(exchangeDAO).execute(code).get()
}

private class SelectAsyncTask internal constructor(private val mAsyncTaskDao: ExchangeDAO) : AsyncTask<String, Void, Int>() {
    override fun doInBackground(vararg p0: String?): Int? {
        return mAsyncTaskDao.getItemCounts(p0[0].toString())
    }

}

private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: ExchangeDAO) : AsyncTask<ExchangeEntity, Void, Void>() {

    override fun doInBackground(vararg moneyList: ExchangeEntity): Void? {
        mAsyncTaskDao.insertItem(moneyList[0])
        return null
    }

}