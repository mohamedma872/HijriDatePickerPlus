package com.sdody.HijriDatePickerPlusApplication
import android.icu.util.IslamicCalendar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Hijri calendar data for the selected year

        println("Days in Muharram: ${getHijriDaysInMonth(1309, 0)}")
        println("Days in Safar: ${getHijriDaysInMonth(1309, 1)}")

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

@Composable
fun HeaderSection(islamicCalendar: IslamicCalendar, onYearClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().background(Color(0xFF008080)).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the weekday at the top
        Text(text = getWeekday(islamicCalendar), color = Color.White, fontSize = 18.sp)

        // Display the month abbreviation and day number
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = getHijriMonthAbbr(islamicCalendar.get(Calendar.MONTH)), color = Color.White, fontSize = 30.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = islamicCalendar.get(Calendar.DAY_OF_MONTH).toString(), color = Color.White, fontSize = 40.sp)
        }

        // Display the year at the bottom and make it clickable to trigger year selection
        Text(
            text = islamicCalendar.get(Calendar.YEAR).toString(),
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.clickable { onYearClick() } // Trigger year selection when the year is clicked
        )
    }
}

@Composable
fun YearSelectionScreen(
    selectedYear: Int,
    onYearSelected: (Int) -> Unit,
    currentYear: Int // Pass the current year to scroll into view
) {
    // The valid range of years for the Umm Al-Qura calendar
    val minHijriYear = 1300 // Starting year (~1900 AD)
    val maxHijriYear = 1500 // Ending year (~2077 AD)
    val years = (minHijriYear..maxHijriYear).toList() // Generate the valid range of years

    // State to scroll directly to the current year index
    val listState = rememberLazyListState()

    // Automatically scroll to the current year when the year selection screen appears
    LaunchedEffect(Unit) {
        listState.scrollToItem(currentYear - minHijriYear) // Scroll to the current year index
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(years) { year ->
            Text(
                text = "$year",
                fontSize = 28.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { onYearSelected(year) }, // Year is selected on click
                color = if (year == selectedYear) Color.Blue else Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun MonthGridWithDays(
    selectedYear: Int,
    onDaySelected: (Int, Int, Int) -> Unit,
    preselectedMonth: Int,
    preselectedDay: Int
) {
    // State to track the selected day and month
    var selectedMonthState by remember { mutableStateOf(preselectedMonth) }
    var selectedDayState by remember { mutableStateOf(preselectedDay) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
    ) {
        items(12) { month ->
            // Month Header
            Text(
                text = getHijriMonthName(month),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                color = Color(0xFF008080)
            )

            // Retrieve cached days for the current month
            val daysInMonth = HijriCalendarDataCache.getDaysForMonth(selectedYear, month)

            // Wrapping LazyVerticalGrid in a fixed height to avoid performance issues
            Box(
                modifier = Modifier.fillMaxWidth().height(200.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(7), // 7 days in a week
                    modifier = Modifier.fillMaxSize(),
                    content = {
                        items(daysInMonth) { day ->
                            val adjustedDay = day + 1
                            Box(
                                modifier = Modifier.size(40.dp).clickable {
                                    selectedMonthState = month
                                    selectedDayState = adjustedDay
                                    onDaySelected(selectedYear, month, adjustedDay)
                                }.background(
                                    if (month == selectedMonthState && adjustedDay == selectedDayState)
                                        Color(0xFF008080) else Color.LightGray
                                ).padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "$adjustedDay", fontSize = 16.sp, color = Color.White)
                            }
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun FooterSection(nextMonthName: String, onConfirm: () -> Unit, onCancel: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the name of the next month
        Text(text = nextMonthName, fontSize = 18.sp, modifier = Modifier.padding(bottom = 8.dp))

        // Cancel and Confirm buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onCancel) {
                Text(text = "Cancel")
            }
            Button(onClick = onConfirm) {
                Text(text = "OK")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHijriDatePickerButton() {
    // Get the current Hijri date


    HijriDatePickerButton()
}

@Preview(showBackground = true)
@Composable
fun PreviewHeaderSection() {
    HeaderSection(IslamicCalendar(1446, 6, 9)){
       // showYearSelection = true // When the year is clicked, show the year selection screen
    }


}

@Preview(showBackground = true)
@Composable
fun PreviewFooterSection() {
    FooterSection(
        nextMonthName = "Rajab",
        onConfirm = {},
        onCancel = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewYearSelectionScreen() {
//    YearSelectionScreen(
//        selectedYear = 1446,
//        onYearSelected = {}
//    )
}

@Preview(showBackground = true)
@Composable
fun PreviewHijriDatePickerDialogWithThreeSections() {
    val daysInMonth = getHijriDaysInMonth(1446, 0) // Muharram in 1446
    println("Days in Muharram 1446: $daysInMonth")

    HijriCalendarDataCache.initializeForYear(1446)

    HijriDatePickerDialogWithThreeSections(
        initialYear = 1446,
        initialMonth = 2,
        initialDay = 9,
        onDateSelected = { _, _, _ -> },
        onConfirm = {},
        onDismissRequest = {},
        initialShowYearSelection = false // Set this to true to show the year selection

    )
}
