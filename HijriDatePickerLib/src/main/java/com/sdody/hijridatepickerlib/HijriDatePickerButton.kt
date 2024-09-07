package com.sdody.hijridatepickerpluslib

import android.icu.util.IslamicCalendar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun HijriDatePickerButton(

) {
    // Get the current Hijri date
    val currentHijriCalendar = IslamicCalendar()
    val currentHijriYear = currentHijriCalendar.get(Calendar.YEAR)
    val currentHijriMonth = currentHijriCalendar.get(Calendar.MONTH)
    val currentHijriDay = currentHijriCalendar.get(Calendar.DAY_OF_MONTH)

    HijriCalendarDataCache.initializeForYear(currentHijriYear)

    // State to hold the selected Hijri date, starting with the current Hijri date
    val selectedDate = remember { mutableStateOf("$currentHijriDay-${getHijriMonthName(currentHijriMonth)}-$currentHijriDay") }

    // State to control dialog visibility
    var showDialog by remember { mutableStateOf(false) }

    // State for preselected date (used when reopening the picker)
    val preselectedYear = remember { mutableStateOf(currentHijriYear) }
    val preselectedMonth = remember { mutableStateOf(currentHijriMonth) }
    val preselectedDay = remember { mutableStateOf(currentHijriDay) }

    // Center the button in a Box
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Button to show the Hijri Date Picker
        Button(
            onClick = {
                showDialog = true // Open the dialog
            },
            modifier = Modifier.width(250.dp).height(70.dp)
        ) {
            Text(
                text = selectedDate.value,
                fontSize = 20.sp // Increase the font size for better visibility
            )
        }
    }

    // Show the custom Hijri Date Picker Dialog only when showDialog is true
    if (showDialog) {
        HijriDatePickerDialogWithThreeSections(
            initialYear = preselectedYear.value,
            initialMonth = preselectedMonth.value,
            initialDay = preselectedDay.value,
            onDateSelected = { year, month, day ->
                // Update the selected date state
                selectedDate.value = "$day-${getHijriMonthName(month)}-$year"
                // Update the preselected date for next opening
                preselectedYear.value = year
                preselectedMonth.value = month
                preselectedDay.value = day
            },
            onConfirm = {
                showDialog = false // Close the dialog when Confirm is clicked
            },
            onDismissRequest = {
                showDialog = false // Close the dialog when Cancel is clicked or dismissed
            },
            initialShowYearSelection = true // Always show year selection first
        )
    }
}