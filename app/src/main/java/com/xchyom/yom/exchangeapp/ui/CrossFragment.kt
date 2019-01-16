package com.xchyom.yom.exchangeapp.ui


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xchyom.yom.exchangeapp.R
import com.xchyom.yom.exchangeapp.entity.CrossValuesEntity
import com.xchyom.yom.exchangeapp.viewmodel.CrossValuesViewModel


class CrossFragment : DialogFragment() {

    private var quantity: EditText? = null
    private var result: TextView? = null
    private var button: Button? = null
    var spinnerFrom: Spinner? = null
    var spinnerTo: Spinner? = null
    var moneyValues = ArrayList<CrossValuesEntity>()
    private lateinit var crossValuesViewModel: CrossValuesViewModel


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.fragment_cross, null)
        this.quantity = view.findViewById(R.id.quantity)
        this.button = view.findViewById(R.id.somar)
        val alert = AlertDialog.Builder(activity)
        alert.setView(view)
        crossValuesViewModel = ViewModelProviders.of(this).get(CrossValuesViewModel::class.java)

        val crossValuesAdapter = ArrayAdapter<Any>(context, android.R.layout.simple_spinner_item)
        crossValuesViewModel.allList.observe(this, Observer { values ->
            values?.forEach {
                crossValuesAdapter.add(it.moneyType)
                moneyValues.add(CrossValuesEntity(it.code, it.moneyType, it.valueSelling, it.valueBuying))
            }
        })
        this.spinnerFrom = view.findViewById(R.id.spinnerFrom)
        this.spinnerTo = view.findViewById(R.id.spinnerTo)
        this.result = view.findViewById(R.id.result)
        spinnerFrom!!.adapter = crossValuesAdapter
        spinnerTo!!.adapter = crossValuesAdapter

        this.button!!.setOnClickListener {
            Log.e("Secili İtem", "${spinnerFrom!!.selectedItemId}")
            result!!.text = (moneyValues[spinnerFrom!!.selectedItemId.toInt()].valueSelling.toDouble() / moneyValues[spinnerTo!!.selectedItemId.toInt()].valueSelling.toDouble() * quantity!!.text.toString().toDouble()).toString()
        }
        return alert.create()
    }

}
