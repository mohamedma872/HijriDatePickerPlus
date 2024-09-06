package com.sdody.hijridatepickerpluslib

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

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

@Composable
fun showHijriDatePicker(
    initialYear: Int,
    initialMonth: Int,
    initialDay: Int,
    onDateSelected: (Int, Int, Int) -> Unit,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit
) {
    // State for preselected date
    val preselectedYear = remember { mutableStateOf(initialYear) }
    val preselectedMonth = remember { mutableStateOf(initialMonth) }
    val preselectedDay = remember { mutableStateOf(initialDay) }

    // Dialog visibility is controlled outside (no button, it will be triggered by external events)
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        HijriDatePickerDialogWithThreeSections(
            initialYear = preselectedYear.value,
            initialMonth = preselectedMonth.value,
            initialDay = preselectedDay.value,
            onDateSelected = { year, month, day ->
                // Update the preselected date for the next opening
                preselectedYear.value = year
                preselectedMonth.value = month
                preselectedDay.value = day
                onDateSelected(year, month, day) // Call the provided callback
            },
            onConfirm = {
                showDialog = false // Close the dialog when Confirm is clicked
                onConfirm() // Call the confirm callback
            },
            onDismissRequest = {
                showDialog = false // Close the dialog when dismissed
                onDismissRequest() // Call the dismiss callback
            },
            initialShowYearSelection = true // Always show year selection first
        )
    }
}