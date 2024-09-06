package com.sdody.hijridatepickerpluslib

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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