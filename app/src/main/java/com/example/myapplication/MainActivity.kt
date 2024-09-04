package com.example.myapplication
import android.icu.util.IslamicCalendar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar
import org.joda.time.DateTime
import org.joda.time.chrono.IslamicChronology
import java.time.chrono.HijrahDate
import java.time.temporal.ChronoField
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Hijri calendar data for the selected year
        HijriCalendarDataCache.initializeForYear(1446)

        println("Days in Muharram: ${getHijriDaysInMonth(1309, 0)}")
        println("Days in Safar: ${getHijriDaysInMonth(1309, 1)}")

        println("Days in Muharram: ${getHijriDaysInMonth2(1309, 0)}")
        println("Days in Safar: ${getHijriDaysInMonth2(1309, 1)}")

        println("Days in Muharram: ${getHijriDaysInMonth3(1309, 0)}")
        println("Days in Safar: ${getHijriDaysInMonth3(1309, 1)}")

        println("Days in Muharram: ${getHijriDaysInMonth4(1309, 0)}")
        println("Days in Safar: ${getHijriDaysInMonth4(1309, 1)}")


        println("Days in Muharram: ${getHijriDaysInMonth5(1309, 0)}")
        println("Days in Safar: ${getHijriDaysInMonth5(1309, 1)}")

        println("Days in Muharram: ${getHijriDaysInMonth6(1309, 0)}")
        println("Days in Safar: ${getHijriDaysInMonth6(1309, 1)}")

        setContent {
            HijriDatePickerButton()
        }
    }
}

@Composable
fun HijriDatePickerButton() {
    // State to hold the selected Hijri date
    val selectedDate = remember { mutableStateOf("Select Hijri Date") }

    // State to control dialog visibility - initialized to false
    var showDialog by remember { mutableStateOf(false) }

    // Button to show the Hijri Date Picker
    Button(onClick = { showDialog = true }) {
        Text(text = selectedDate.value)
    }

    // Show the custom Hijri Date Picker Dialog only when showDialog is true
    if (showDialog) {
        HijriDatePickerDialogWithThreeSections(
            initialYear = 1446,
            initialMonth = 1,  // Example starting month
            initialDay = 1,     // Example starting day
            onDateSelected = { year, month, day ->
                selectedDate.value = "$day-${getHijriMonthName(month)}-$year"
                //showDialog = false // Close the dialog after selection
            },
            onConfirm = {
                showDialog = false // Close the dialog when Confirm is clicked
            },
            onDismissRequest = {
                showDialog = false // Close the dialog when Cancel is clicked or dismissed
            },
            initialShowYearSelection = true
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
    initialShowYearSelection: Boolean = true // Start by showing year selection
) {
    var selectedYear by remember { mutableStateOf(initialYear) }
    var selectedMonth by remember { mutableStateOf(initialMonth) }
    var selectedDay by remember { mutableStateOf(initialDay) }
    var showYearSelection by remember { mutableStateOf(initialShowYearSelection) } // Toggle between year selection and month grid

    // Ensure selected day is valid for the selected month
    val daysInMonth = getHijriDaysInMonth(selectedYear, selectedMonth)
    if (selectedDay > daysInMonth) {
        selectedDay = daysInMonth
    }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 400.dp, max = 500.dp) // Limit the height to a smaller size
                .padding(16.dp), // Add some padding around the dialog
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                // Update the header with the current selected date
                val cal = IslamicCalendar(selectedYear, selectedMonth, selectedDay)

                // Pass the callback to trigger year selection
                HeaderSection(islamicCalendar = cal) {
                    showYearSelection = true // When the year is clicked, show the year selection screen
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Check if we need to show the year selection or the month grid
                if (showYearSelection) {
                    // Year Selection
                    YearSelectionScreen(
                        selectedYear = selectedYear,
                        onYearSelected = { year ->
                            selectedYear = year
                            showYearSelection = false // Switch to month grid after year is selected
                        }
                    )
                } else {
                    // Month Grid with Days Section
                    Box(
                        modifier = Modifier
                            .weight(1f) // This makes the grid take up remaining space without pushing buttons off-screen
                            .fillMaxWidth()
                    ) {
                        MonthGridWithDays(
                            selectedYear = selectedYear,
                            onDaySelected = { year, month, day ->
                                selectedYear = year
                                selectedMonth = month
                                selectedDay = day
                                onDateSelected(year, month, day)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Update the footer with the next month based on the selected month
                FooterSection(
                    nextMonthName = getHijriMonthName(selectedMonth + 1),
                    onConfirm = onConfirm,
                    onCancel = onDismissRequest
                )
            }
        }
    }
}



@Composable
fun YearSelectionScreen(
    selectedYear: Int,
    onYearSelected: (Int) -> Unit
) {
    // The valid range of years for the Umm Al-Qura calendar
    val minHijriYear = 1300 // Starting year (~1900 AD)
    val maxHijriYear = 1500 // Ending year (~2077 AD)
    val years = (minHijriYear..maxHijriYear).toList() // Generate the valid range of years

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally // Center all content horizontally
    ) {
        // Scrollable list of years
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally // Center the text in the column
        ) {
            years.forEach { year ->
                Text(
                    text = "$year",
                    fontSize = 28.sp, // Increase the font size for the year
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Increase padding around each year
                        .clickable { onYearSelected(year) }, // Year is selected on click
                    color = if (year == selectedYear) Color.Blue else Color.Black,
                    textAlign = TextAlign.Center // Center the year text
                )
            }
        }
    }
}






@Composable
fun HeaderSection(islamicCalendar: IslamicCalendar, onYearClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF008080)) // Use a teal-like color similar to the image
            .padding(16.dp),
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

        // Display the year at the bottom and make it clickable
        Text(
            text = islamicCalendar.get(Calendar.YEAR).toString(),
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.clickable { onYearClick() } // Clicking the year triggers the year selection
        )
    }
}

// Helper function to get weekday name
fun getWeekday(islamicCalendar: IslamicCalendar): String {
    return when (islamicCalendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.SUNDAY -> "Sunday"
        Calendar.MONDAY -> "Monday"
        Calendar.TUESDAY -> "Tuesday"
        Calendar.WEDNESDAY -> "Wednesday"
        Calendar.THURSDAY -> "Thursday"
        Calendar.FRIDAY -> "Friday"
        Calendar.SATURDAY -> "Saturday"
        else -> ""
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

fun getWeekday(hijriCalendar: UmmalquraCalendar): String {
    return when (hijriCalendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.SUNDAY -> "Sunday"
        Calendar.MONDAY -> "Monday"
        Calendar.TUESDAY -> "Tuesday"
        Calendar.WEDNESDAY -> "Wednesday"
        Calendar.THURSDAY -> "Thursday"
        Calendar.FRIDAY -> "Friday"
        Calendar.SATURDAY -> "Saturday"
        else -> ""
    }
}

fun getHijriMonthAbbr(month: Int): String {
    return when (month) {
        0 -> "MUH"
        1 -> "SAF"
        2 -> "RAB-I"
        3 -> "RAB-II"
        4 -> "JUM-I"
        5 -> "JUM-II"
        6 -> "RAJ"
        7 -> "SHA"
        8 -> "RAM"
        9 -> "SHAW"
        10 -> "DHU-Q"
        11 -> "DHU-H"
        else -> ""
    }
}

fun getHijriMonthName(month: Int): String {
    return when (month % 12) { // Handle month overflow
        0 -> "Muharram"
        1 -> "Safar"
        2 -> "Rabi' al-Awwal"
        3 -> "Rabi' al-Thani"
        4 -> "Jumada al-Awwal"
        5 -> "Jumada al-Thani"
        6 -> "Rajab"
        7 -> "Sha'ban"
        8 -> "Ramadan"
        9 -> "Shawwal"
        10 -> "Dhu al-Qi'dah"
        11 -> "Dhu al-Hijjah"
        else -> ""
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MonthGridWithDays(
    selectedYear: Int,
    onDaySelected: (Int, Int, Int) -> Unit
) {
    // State to track the selected day and month
    var selectedMonthState by remember { mutableStateOf(0) }
    var selectedDayState by remember { mutableStateOf(1) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(12) { month ->
            // Month Header
            Text(
                text = getHijriMonthName(month),
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = Color(0xFF008080)
            )

            // Retrieve cached days for the current month
            val daysInMonth = getHijriDaysInMonth(selectedYear,month)

            // Wrapping LazyVerticalGrid in a fixed height to avoid performance issues
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // Adjust height to fit your UI
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(7), // 7 days in a week
                    modifier = Modifier.fillMaxSize(),
                    content = {
                        items(daysInMonth) { day ->
                            val adjustedDay = day+1
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clickable {
                                        selectedMonthState = month
                                        selectedDayState = adjustedDay
                                        onDaySelected(selectedYear, month, adjustedDay)
                                    }
                                    .background(
                                        if (month == selectedMonthState && adjustedDay == selectedDayState)
                                            Color(0xFF008080) else Color.LightGray
                                    )
                                    .padding(8.dp),
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


fun getHijriDaysInMonth(year: Int, month: Int): Int {
    return try {
        val hijriCalendar = UmmalquraCalendar(year, month, 1)
        hijriCalendar.add(Calendar.MONTH, 1)
        hijriCalendar.set(Calendar.DAY_OF_MONTH, 1)
        hijriCalendar.add(Calendar.DAY_OF_MONTH, -1)
        hijriCalendar.get(Calendar.DAY_OF_MONTH)
    } catch (e: Exception) {
        30 // Default to 30 days if there's an error
    }
}

fun getHijriDaysInMonth2(year: Int, month: Int): Int {
    return try {
        val hijriCalendar = UmmalquraCalendar(year, month, 1) // Initialize to the 1st day of the month
        hijriCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) // Get the actual maximum number of days in the month
    } catch (e: Exception) {
        // Log or handle the error if necessary
        30 // Return a default value of 30 days if there's an error
    }
}

fun getHijriDaysInMonth3(year: Int, month: Int): Int {
    return try {
        // Initialize the calendar with the 1st day of the given month
        val hijriCalendar = UmmalquraCalendar(year, month, 1)

        // Set the date to the first day of the next month
        hijriCalendar.add(Calendar.MONTH, 1)
        hijriCalendar.set(Calendar.DAY_OF_MONTH, 1)

        // Subtract one day to get the last day of the current month
        hijriCalendar.add(Calendar.DAY_OF_MONTH, -1)

        // Return the day number of the last day, which gives the number of days in the month
        hijriCalendar.get(Calendar.DAY_OF_MONTH)
    } catch (e: Exception) {
        e.printStackTrace()
        30 // Return 30 days as a default in case of error
    }
}
fun getHijriDaysInMonth4(year: Int, month: Int): Int {
    return try {
        // Create a HijrahDate for the first day of the given month
        val hijrahDate = HijrahDate.of(year, month, 1)

        // Get the maximum value for the DAY_OF_MONTH field, i.e., the number of days in the month
        hijrahDate.range(ChronoField.DAY_OF_MONTH).maximum.toInt()
    } catch (e: Exception) {
        e.printStackTrace()
        30 // Default to 30 days if there's an error
    }
}
object HijriCalendarDataCache {

    private val cachedMonthDays = mutableMapOf<Int, List<Int>>()

    // Precompute all the months and their days for the given year
    fun initializeForYear(year: Int) {
        for (month in 0..11) {
            cachedMonthDays[month] = getDaysInHijriMonth(year, month)
        }
    }

    // Retrieve the cached days for a given month
    fun getDaysForMonth(month: Int): List<Int> {
        return cachedMonthDays[month] ?: emptyList()
    }

    // Compute the days in a Hijri month
    private fun getDaysInHijriMonth(year: Int, month: Int): List<Int> {
        return try {
            val hijriCalendar = UmmalquraCalendar(year, month, 1)
            hijriCalendar.add(Calendar.MONTH, 1)
            hijriCalendar.set(Calendar.DAY_OF_MONTH, 1)
            hijriCalendar.add(Calendar.DAY_OF_MONTH, -1)
            (1..hijriCalendar.get(Calendar.DAY_OF_MONTH)).toList()
        } catch (e: Exception) {
            emptyList() // Return an empty list if there's an error
        }
    }
}

fun getHijriDaysInMonth5(year: Int, month: Int): Int {
    return try {
        // Create a DateTime object using the IslamicChronology
        val islamicDate = DateTime.now().withChronology(IslamicChronology.getInstance())
            .withYear(year)
            .withMonthOfYear(month)
            .withDayOfMonth(1)

        // Get the maximum number of days in the given month
        islamicDate.dayOfMonth().maximumValue
    } catch (e: Exception) {
        e.printStackTrace()
        30 // Default to 30 days in case of error
    }
}

fun getHijriDaysInMonth6(year: Int, month: Int): Int {
    return try {
        // Create an IslamicCalendar object for the specified year and month
        val islamicCalendar = IslamicCalendar(Locale.getDefault())
        islamicCalendar.set(IslamicCalendar.EXTENDED_YEAR, 1446)
        islamicCalendar.set(IslamicCalendar.MONTH, IslamicCalendar.MUHARRAM) // MONTH is 0-indexed

        // Get the maximum day of the month (i.e., number of days in the month)
        islamicCalendar.getActualMaximum(IslamicCalendar.DAY_OF_MONTH)
    } catch (e: Exception) {
        e.printStackTrace()
        30 // Default to 30 days in case of error
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHijriDatePickerButton() {
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
    YearSelectionScreen(
        selectedYear = 1446,
        onYearSelected = {}
    )
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
