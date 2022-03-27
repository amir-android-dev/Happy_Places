package com.amir.happyplaces

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_add_happy_places.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * to make whole class an onClickListener, we follow the instructions of below
 */
class AddHappyPlacesActivity : AppCompatActivity(), View.OnClickListener {
    //from java.util
    private var cal = Calendar.getInstance()
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_happy_places)

        setSupportActionBar(toolbar_add_place)

        //this line will add the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar_add_place?.setNavigationOnClickListener {
            onBackPressed()
        }
//OnDateSetListener: is very similar ro setNavigationListener, but here we wait to someone set the date
        dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }
        et_date.setOnClickListener(this)
    }

    //instead of to write for every item an OnclickListener, we follow this instruction
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.et_date -> {
                DatePickerDialog(
                    this@AddHappyPlacesActivity,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)

                ).show()
            }

        }
    }

    private fun updateDateInView(){
        val mFormat = "dd.MM.yyyy"
        val sdf= SimpleDateFormat(mFormat, Locale.getDefault())
        et_date.setText(sdf.format(cal.time).toString())
    }
}