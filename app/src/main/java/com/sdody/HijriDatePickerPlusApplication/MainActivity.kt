package com.sdody.HijriDatePickerPlusApplication

import android.icu.util.Calendar
import android.icu.util.ULocale
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sdody.hijridatepickerpluslib.HijriDatePickerButton
import com.sdody.hijridatepickerpluslib.showHijriDatePicker

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    var selectedCalendarType by remember { mutableStateOf("umalqura") }
    var showDatePicker by remember { mutableStateOf(false) }

    // Derived state to compute the current date for the selected calendar type.
    // This recomputes whenever selectedCalendarType changes.
    val (initialYear, initialMonth, initialDay) = remember(selectedCalendarType) {
        val locale = when (selectedCalendarType) {
            "umalqura" -> ULocale("@calendar=islamic-umalqura")
            "civil" -> ULocale("@calendar=islamic-civil")
            "islamic" -> ULocale("@calendar=islamic")
            else -> ULocale("@calendar=islamic-umalqura")
        }
        val calendar = Calendar.getInstance(locale)
        Triple(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Dropdown to select calendar type
        CalendarTypeSelector(
            selectedType = selectedCalendarType,
            onTypeSelected = { newType ->
                selectedCalendarType = newType
                // Optionally, if you want to force the dialog to close when switching calendars:
                showDatePicker = false
            }
        )

        // Button for Hijri Date Picker with dynamic calendar type
        HijriDatePickerButton(calendarType = selectedCalendarType)

        // Button to trigger the date picker dialog
        Button(onClick = { showDatePicker = true }) {
            Text("Show Hijri Date Picker")
        }

        // Wrap the date picker dialog with a key that depends on selectedCalendarType.
        // This forces a recomposition of the dialog when the calendar type changes.
        if (showDatePicker) {
            key(selectedCalendarType) {
                showHijriDatePicker(
                    initialYear = initialYear,
                    initialMonth = initialMonth,
                    initialDay = initialDay,
                    onDateSelected = { year, month, day ->
                        println("Selected date: $year-$month-$day")
                    },
                    onConfirm = { year, month, day ->
                        println("Confirmed date: $year-$month-$day")
                        showDatePicker = false // Close dialog on confirm
                    },
                    onDismissRequest = {
                        println("Date picker dismissed")
                        showDatePicker = false // Close dialog on dismiss
                    },
                    calendarType = selectedCalendarType
                )
            }
        }
    }
}

@Composable
fun CalendarTypeSelector(selectedType: String, onTypeSelected: (String) -> Unit) {
    val calendarTypes = listOf("umalqura", "civil", "islamic")
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text("Calendar Type: $selectedType")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            calendarTypes.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type) },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}

fun getDaysInMonthForCalendarType(year: Int, month: Int, calendarType: String): Int {
    val locale = when (calendarType) {
        "umalqura" -> ULocale("@calendar=islamic-umalqura")
        "civil" -> ULocale("@calendar=islamic-civil")
        "islamic" -> ULocale("@calendar=islamic")
        else -> ULocale("@calendar=islamic-umalqura")
    }
    val calendar = Calendar.getInstance(locale)
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month)
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}
