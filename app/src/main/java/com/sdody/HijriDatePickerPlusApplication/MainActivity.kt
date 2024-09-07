package com.sdody.HijriDatePickerPlusApplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sdody.hijridatepickerpluslib.HijriDatePickerButton


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Hijri calendar data for the selected year

       // println("Days in Muharram: ${getHijriDaysInMonth(1309, 0)}")
       // println("Days in Safar: ${getHijriDaysInMonth(1309, 1)}")

        setContent {

            HijriDatePickerButton()

            // Example of how you can trigger the date picker from anywhere
          /*  showHijriDatePicker(
                initialYear = 1446, // Pass the initial year, e.g., current Hijri year
                initialMonth = 1,   // Initial month
                initialDay = 10,    // Initial day
                onDateSelected = { year, month, day ->
                    // Handle date selected (year, month, day)
                    println("Selected Date: $day-${getHijriMonthName(month)}-$year")
                },
                onConfirm = {
                    // Handle confirm click
                    println("Date Picker Confirmed")
                },
                onDismissRequest = {
                    // Handle dismiss
                    println("Date Picker Dismissed")
                }
            )*/
        }
    }
}
