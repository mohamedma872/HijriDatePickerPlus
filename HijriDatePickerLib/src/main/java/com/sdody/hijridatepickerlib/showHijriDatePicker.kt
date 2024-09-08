package com.sdody.hijridatepickerpluslib

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

// Example of how you can trigger the date picker from anywhere
/*  showHijriDatePicker(
    initialYear = 1445,
    initialMonth = 1,
    initialDay = 1,
    onDateSelected = { year, month, day ->
        // Handle date selection changes
        println("Selected date: $year-$month-$day")
    },
    onConfirm = { year, month, day ->
        // Handle the final confirmed date here
        println("Confirmed date: $year-$month-$day")
    },
    onDismissRequest = {
        // Handle dialog dismissal without confirmation
        println("Date picker dismissed")
    },
    calendarType = "umalqura" // Use "umalqura", "civil", or "islamic"
)
*/
@Composable
fun showHijriDatePicker(
    initialYear: Int,
    initialMonth: Int,
    initialDay: Int,
    onDateSelected: (Int, Int, Int) -> Unit,
    onConfirm: (Int, Int, Int) -> Unit, // Modify to pass the selected date on confirm
    onDismissRequest: () -> Unit,
    calendarType: String
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
                // Pass the selected date to the confirm callback
                onConfirm(preselectedYear.value, preselectedMonth.value, preselectedDay.value)
            },
            onDismissRequest = {
                showDialog = false // Close the dialog when dismissed
                onDismissRequest() // Call the dismiss callback
            },
            initialShowYearSelection = true, // Always show year selection first
            calendarType = calendarType // "umalqura", "civil", or "islamic"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHijriDatePicker() {
    // Preview the Hijri Date Picker with some predefined values
    showHijriDatePicker(
        initialYear = 1445,  // Initial year in Hijri calendar
        initialMonth = 1,    // Safar (0-indexed month)
        initialDay = 1,      // 1st day of Safar
        onDateSelected = { year, month, day ->
            // Handle date selection (preview action)
        },
        onConfirm = { year, month, day ->
            // Handle confirmation (preview action)
        },
        onDismissRequest = {
            // Handle dismissal (preview action)
        },
        calendarType = "umalqura"  // Calendar type for preview
    )
}