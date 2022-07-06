package com.example.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById<Button>(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
        btnDatePicker.setOnClickListener{
            clickDatePicker()
        }
    }

    private fun clickDatePicker(){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd =  DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDayOfMonth ->

                // Toast.makeText(this, "Year was $selectedYear , month was ${selectedMonth+1} , day was $selectedDayOfMonth",Toast.LENGTH_LONG).show()

                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                tvSelectedDate?.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                val theDate = sdf.parse(selectedDate)
                //null safety date - only if theDate is not empty run this code
                theDate?.let {
                    // *1000 for seconds , *60 for minutes from
                    val selectedDateInMinutes = theDate.time / 60000
                    // get time in milliseconds
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val curentDateInMinutes = currentDate.time / 60000
                        val diffrenceInMinutes = curentDateInMinutes - selectedDateInMinutes

                        tvAgeInMinutes?.text = diffrenceInMinutes.toString()
                    }

                }

            },
            year,
            month,
            day
        )
        //moved the data picker code in val dpd so we can access property max time
        // so we will not be able to select date in teh future -- set maxDate to be yesterday ( curent date -24h - in miliseconds)
       dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}