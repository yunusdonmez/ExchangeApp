package com.xchyom.yom.exchangeapp.ui


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xchyom.yom.exchangeapp.R
import com.xchyom.yom.exchangeapp.entity.CrossValuesEntity
import com.xchyom.yom.exchangeapp.toast
import com.xchyom.yom.exchangeapp.viewmodel.CrossValuesViewModel
import java.text.DecimalFormat


class CrossFragment : DialogFragment() {

    private var quantity: EditText? = null
    private var result: TextView? = null
    private var button: Button? = null
    private var spinnerFrom: Spinner? = null
    private var spinnerTo: Spinner? = null
    private var moneyValues = ArrayList<CrossValuesEntity>()
    private lateinit var crossValuesViewModel: CrossValuesViewModel


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.fragment_cross, null)
        this.quantity = view.findViewById(R.id.quantity)
        this.button = view.findViewById(R.id.calculate)
        val alert = AlertDialog.Builder(activity)
        alert.setView(view)
        crossValuesViewModel = ViewModelProviders.of(this).get(CrossValuesViewModel::class.java)

        val crossValuesAdapter = ArrayAdapter<Any>(context!!, R.layout.spinner_list_style)
        crossValuesViewModel.allList.observe(this, Observer { values ->
            values?.forEach {
                crossValuesAdapter.add(it.moneyType)
                moneyValues.add(CrossValuesEntity(it.code, it.moneyType, it.valueSelling, it.valueBuying))
            }
        })
        this.spinnerFrom = view.findViewById(R.id.spinnerFrom)
        this.spinnerTo = view.findViewById(R.id.spinnerTo)
        this.result = view.findViewById(R.id.result)

        crossValuesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerFrom!!.adapter = crossValuesAdapter
        spinnerTo!!.adapter = crossValuesAdapter

        //calculate
        this.button!!.setOnClickListener {
            //Log.e("Secili İtem", "${spinnerFrom!!.selectedItemId}")
            if (quantity!!.text.toString() != "") {
                val selling = moneyValues[spinnerFrom!!.selectedItemId.toInt()].valueSelling.toDouble()
                val buying = moneyValues[spinnerTo!!.selectedItemId.toInt()].valueSelling.toDouble()
                val df = DecimalFormat("#.####")
                val tmpResult = selling / buying * quantity!!.text.toString().toDouble()
                result!!.text = quantity!!.text.toString() + " " + moneyValues[spinnerFrom!!.selectedItemId.toInt()].code + "=" + (df.format(tmpResult)).toString() + " " + moneyValues[spinnerTo!!.selectedItemId.toInt()].code

            } else
                "Lütfen Geçerli Bir Değer Giriniz".toast(button!!.context)
        }
        return alert.create()
    }

}
