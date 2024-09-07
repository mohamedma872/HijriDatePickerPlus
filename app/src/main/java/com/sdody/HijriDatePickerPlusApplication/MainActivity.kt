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
           // HijriDatePickerButton()

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
