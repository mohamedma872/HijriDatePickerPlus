package com.sdody.hijridatepickerpluslib

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun HijriDatePickerDialogWithThreeSections(
    initialYear: Int,
    initialMonth: Int,
    initialDay: Int,
    onDateSelected: (Int, Int, Int) -> Unit,  // Call this when date is selected
    onConfirm: (Int, Int, Int) -> Unit,  // Pass the selected year, month, day
    onDismissRequest: () -> Unit,
    initialShowYearSelection: Boolean = true, // Always show year selection when opening the dialog
    calendarType: String
) {
    // Key the state with calendarType so that if it changes the state is reinitialized
    var selectedYear by remember(calendarType) { mutableStateOf(initialYear) }
    var selectedMonth by remember(calendarType) { mutableStateOf(initialMonth) }
    var selectedDay by remember(calendarType) { mutableStateOf(initialDay) }
    var showYearSelection by remember(calendarType) { mutableStateOf(initialShowYearSelection) }

    // Ensure selected day is valid for the selected month
    val daysInMonth = getHijriDaysInMonth(selectedYear, selectedMonth, calendarType)
    if (selectedDay > daysInMonth) {
        selectedDay = daysInMonth
    }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 400.dp, max = 500.dp)
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                // Create a calendar instance based on the calendarType and update it with the current selection
                val calendar = getIslamicCalendar(calendarType)
                calendar.set(Calendar.YEAR, selectedYear)
                calendar.set(Calendar.MONTH, selectedMonth)
                calendar.set(Calendar.DAY_OF_MONTH, selectedDay)

                // Header section with the selected date and a callback to toggle year selection
                HeaderSection(calendar = calendar) {
                    showYearSelection = true // Show year selection when the header is clicked
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Show either the year selection or the month grid with days
                if (showYearSelection) {
                    YearSelectionScreen(
                        selectedYear = selectedYear,
                        onYearSelected = { year ->
                            selectedYear = year
                            showYearSelection = false // Return to month grid after selecting a year
                        },
                        currentYear = initialYear // Use the preselected year to focus
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        MonthGridWithDays(
                            selectedYear = selectedYear,
                            onDaySelected = { year, month, day ->
                                selectedYear = year
                                selectedMonth = month
                                selectedDay = day
                                onDateSelected(year, month, day) // Update selected date externally
                            },
                            preselectedMonth = selectedMonth,
                            preselectedDay = selectedDay,
                            calendarType = calendarType
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Footer with Confirm and Cancel buttons
                FooterSection(
                    nextMonthName = getHijriMonthName(selectedMonth),
                    onConfirm = {
                        onConfirm(selectedYear, selectedMonth, selectedDay)
                    },
                    onCancel = onDismissRequest
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHijriDatePickerDialogWithThreeSections() {
    HijriDatePickerDialogWithThreeSections(
        initialYear = 1445, // Example Hijri year
        initialMonth = 1,   // For example, Safar (month index starts at 0)
        initialDay = 5,     // 5th day
        onDateSelected = { year, month, day ->
            println("Date Selected in Preview: $day-${getHijriMonthName(month)}-$year")
        },
        onConfirm = { year, month, day ->
            println("Date Confirmed in Preview: $day-${getHijriMonthName(month)}-$year")
        },
        onDismissRequest = {
            println("Dialog Dismissed in Preview")
        },
        calendarType = "umalqura" // Using the "umalqura" calendar type for this preview
    )
}
