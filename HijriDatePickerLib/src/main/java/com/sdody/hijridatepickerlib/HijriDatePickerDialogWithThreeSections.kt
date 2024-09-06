package com.sdody.hijridatepickerpluslib

import android.icu.util.IslamicCalendar
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun HijriDatePickerDialogWithThreeSections(
    initialYear: Int,
    initialMonth: Int,
    initialDay: Int,
    onDateSelected: (Int, Int, Int) -> Unit,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit,
    initialShowYearSelection: Boolean = true // Always show year selection when opening the dialog
) {
    var selectedYear by remember { mutableStateOf(initialYear) }
    var selectedMonth by remember { mutableStateOf(initialMonth) }
    var selectedDay by remember { mutableStateOf(initialDay) }
    var showYearSelection by remember { mutableStateOf(initialShowYearSelection) }

    // Ensure selected day is valid for the selected month
    val daysInMonth = getHijriDaysInMonth(selectedYear, selectedMonth)
    if (selectedDay > daysInMonth) {
        selectedDay = daysInMonth
    }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier.fillMaxWidth().heightIn(min = 400.dp, max = 500.dp).padding(16.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().background(Color.White)
            ) {
                // Header section with the selected date
                val cal = IslamicCalendar(selectedYear, selectedMonth, selectedDay)

                // Pass the callback to trigger year selection
                HeaderSection(islamicCalendar = cal) {
                    showYearSelection = true // Toggle to show year selection when the year is clicked
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Show either the year selection screen or the month grid
                if (showYearSelection) {
                    YearSelectionScreen(
                        selectedYear = selectedYear,
                        onYearSelected = { year ->
                            selectedYear = year
                            showYearSelection = false // Switch back to month grid after year is selected
                        },
                        currentYear = initialYear // Pass the preselected year to focus on it
                    )
                } else {
                    // Month Grid with Days Section
                    Box(
                        modifier = Modifier.weight(1f).fillMaxWidth()
                    ) {
                        MonthGridWithDays(
                            selectedYear = selectedYear,
                            onDaySelected = { year, month, day ->
                                selectedYear = year
                                selectedMonth = month
                                selectedDay = day
                                onDateSelected(year, month, day)
                            },
                            preselectedMonth = selectedMonth, // Pass the preselected month
                            preselectedDay = selectedDay // Pass the preselected day
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Footer with Confirm and Cancel buttons
                FooterSection(
                    nextMonthName = getHijriMonthName(selectedMonth),
                    onConfirm = onConfirm,
                    onCancel = onDismissRequest
                )
            }
        }
    }
}

