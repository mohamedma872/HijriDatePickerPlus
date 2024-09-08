package com.sdody.hijridatepickerpluslib

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
fun PreviewFooterSection() {
    FooterSection(
        nextMonthName = "Safar",
        onConfirm = { /* Handle confirm action in preview */ },
        onCancel = { /* Handle cancel action in preview */ }
    )
}