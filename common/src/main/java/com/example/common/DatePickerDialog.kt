package com.example.common

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import java.util.*

@Composable
fun DatePicker(mContext: Context, mDate: String, onDateSelected: (String) -> Unit) {

    val mCalendar = Calendar.getInstance()

// Fetching current year, month and day
    val mYear = mCalendar.get(Calendar.YEAR)
    val mMonth = mCalendar.get(Calendar.MONTH)
    val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()


    DatePickerDialog(
        mContext, { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            onDateSelected("$mDayOfMonth/${mMonth + 1}/$mYear")
        }, mYear, mMonth, mDay
    ).show()
}