package com.sdody.HijriDatePickerPlusApplication

import android.icu.util.Calendar
import android.icu.util.ULocale
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sdody.hijridatepickerpluslib.HijriDatePickerButton
import com.sdody.hijridatepickerpluslib.getHijriMonthName
import com.sdody.hijridatepickerpluslib.showHijriDatePicker


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Hijri calendar data for the selected year

       // println("Days in Muharram: ${getHijriDaysInMonth(1309, 0)}")
       // println("Days in Safar: ${getHijriDaysInMonth(1309, 1)}")

        setContent {
           // Use "umalqura", "civil", or "islamic"
           // HijriDatePickerButton(calendarType = "umalqura")
            var lastSelectedYear = 1445
            var lastSelectedMonth = 1
            var lastSelectedDay = 1

            // Example of how you can trigger the date picker from anywhere
            showHijriDatePicker(
                initialYear = lastSelectedYear,
                initialMonth = lastSelectedMonth,
                initialDay = lastSelectedDay,
                onDateSelected = { year, month, day ->
                    // Handle date selection changes
                    lastSelectedYear = year
                    lastSelectedMonth = month
                    lastSelectedDay = day
                    println("Selected date: $year-$month-$day")
                },
                onConfirm = { year, month, day ->
                    // Handle the final confirmed date here
                    lastSelectedYear = year
                    lastSelectedMonth = month
                    lastSelectedDay = day
                    println("Confirmed date: $year-$month-$day")
                },
                onDismissRequest = {
                    // Handle dialog dismissal without confirmation
                    println("Date picker dismissed")
                },
                calendarType = "umalqura" // Use "umalqura", "civil", or "islamic"
            )
        }
    }
}
fun getDaysInMonthForUmmAlQura(year: Int, month: Int): Int {
    // Using the Umm al-Qura Calendar (Saudi Arabia specific Hijri calendar)
    val umalquraLocale = ULocale("@calendar=islamic-umalqura")
    val umalquraCalendar = Calendar.getInstance(umalquraLocale)

    // Set the year and month you want to check (months are 0-indexed, so 0 = Muharram, 1 = Safar, ...)
    umalquraCalendar.set(Calendar.YEAR, year)
    umalquraCalendar.set(Calendar.MONTH, month)

    // Get the number of days in the month
    return umalquraCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}