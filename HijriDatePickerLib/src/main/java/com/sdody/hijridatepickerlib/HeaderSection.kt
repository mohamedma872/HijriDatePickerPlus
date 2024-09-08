package com.sdody.hijridatepickerpluslib

import android.icu.util.Calendar
import android.icu.util.IslamicCalendar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HeaderSection(calendar: Calendar, onYearClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().background(Color(0xFF008080)).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the weekday at the top
        Text(text = getWeekday(calendar), color = Color.White, fontSize = 18.sp)

        // Display the month abbreviation and day number
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = getHijriMonthAbbr(calendar.get(Calendar.MONTH)), color = Color.White, fontSize = 30.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = calendar.get(Calendar.DAY_OF_MONTH).toString(), color = Color.White, fontSize = 40.sp)
        }

        // Display the year at the bottom and make it clickable to trigger year selection
        Text(
            text = calendar.get(Calendar.YEAR).toString(),
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.clickable { onYearClick() } // Trigger year selection when the year is clicked
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHeaderSection() {
    // Creating an instance of IslamicCalendar for preview purposes
    val calendar = IslamicCalendar().apply {
        set(Calendar.YEAR, 1445)
        set(Calendar.MONTH, 1) // Safar
        set(Calendar.DAY_OF_MONTH, 5)
    }

    // Preview the HeaderSection with a mock IslamicCalendar and a simple onYearClick action
    HeaderSection(calendar = calendar, onYearClick = {
        // Handle year click in preview
    })
}