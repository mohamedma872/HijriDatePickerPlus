package com.sdody.hijridatepickerpluslib

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MonthGridWithDays(
    selectedYear: Int,
    onDaySelected: (Int, Int, Int) -> Unit,
    preselectedMonth: Int,
    preselectedDay: Int,
    calendarType : String
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
            val daysInMonth = HijriCalendarDataCache.getDaysForMonth(selectedYear, month, calendarType = calendarType)

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