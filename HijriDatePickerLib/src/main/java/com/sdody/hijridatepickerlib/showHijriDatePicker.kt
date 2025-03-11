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
    // The showDialog state is controlled outside, as this Composable doesn't have buttons.
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        HijriDatePickerDialogWithThreeSections(
            initialYear = initialYear,    // Preselected year from the lifted state
            initialMonth = initialMonth,  // Preselected month from the lifted state
            initialDay = initialDay,      // Preselected day from the lifted state
            onDateSelected = { year, month, day ->
                // Update the selected date in the parent
                onDateSelected(year, month, day)
            },
            onConfirm = { year, month, day ->
                showDialog = false  // Close the dialog when Confirm is clicked
                // Pass the selected date to the confirm callback
                onConfirm(year, month, day)
            },
            onDismissRequest = {
                showDialog = false  // Close the dialog when dismissed
                onDismissRequest()  // Call the dismiss callback
            },
            initialShowYearSelection = true, // Always show year selection first
            calendarType = calendarType // Calendar type ("umalqura", "civil", or "islamic")
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