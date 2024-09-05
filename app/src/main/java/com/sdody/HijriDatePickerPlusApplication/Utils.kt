package com.sdody.HijriDatePickerPlusApplication

import android.icu.util.IslamicCalendar
import java.util.Calendar

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

fun getHijriDaysInMonth(year: Int, month: Int): Int {
    return try {
        val hijriCalendar = IslamicCalendar(year, month, 1)
        hijriCalendar.add(Calendar.MONTH, 1)
        hijriCalendar.set(Calendar.DAY_OF_MONTH, 1)
        hijriCalendar.add(Calendar.DAY_OF_MONTH, -1)
        hijriCalendar.get(Calendar.DAY_OF_MONTH)
    } catch (e: Exception) {
        30 // Default to 30 days if there's an error
    }
}


object HijriCalendarDataCache {

    // Cache days for each year separately, with each year holding month-to-days mapping
    private val cachedYearlyMonthDays = mutableMapOf<Int, MutableMap<Int, Int>>() // Changed to store Int instead of List<Int>

    // Precompute all the months and their number of days for the given year
    fun initializeForYear(year: Int) {
        if (cachedYearlyMonthDays[year] == null) {
            cachedYearlyMonthDays[year] = mutableMapOf()
            for (month in 0..11) {
                cachedYearlyMonthDays[year]?.set(month, getDaysInHijriMonth(year, month))
            }
        }
    }

    // Retrieve cached number of days for a given year and month, and calculate if not cached
    fun getDaysForMonth(year: Int, month: Int): Int {
        // Check if the year is cached
        if (cachedYearlyMonthDays[year] == null) {
            initializeForYear(year) // Initialize the year if not already cached
        }
        // Check if the month is cached
        return cachedYearlyMonthDays[year]?.get(month) ?: getDaysInHijriMonth(year, month)
    }

    // Compute the number of days in a Hijri month and cache it
    private fun getDaysInHijriMonth(year: Int, month: Int): Int {
        // Check if the year is already cached
        if (cachedYearlyMonthDays[year] == null) {
            cachedYearlyMonthDays[year] = mutableMapOf() // Initialize the map for this year if not present
        }

        // Check if the month is already cached
        val cachedDays = cachedYearlyMonthDays[year]?.get(month)
        if (cachedDays != null) {
            return cachedDays // Return cached number of days if available
        }

        // If not cached, calculate the number of days in the month
        return try {
            val hijriCalendar = IslamicCalendar(year, month, 1)
            hijriCalendar.add(Calendar.MONTH, 1)
            hijriCalendar.set(Calendar.DAY_OF_MONTH, 1)
            hijriCalendar.add(Calendar.DAY_OF_MONTH, -1)
            val daysInMonth = hijriCalendar.get(Calendar.DAY_OF_MONTH)

            // Cache the result for future use
            cachedYearlyMonthDays[year]?.put(month, daysInMonth)

            daysInMonth // Return the calculated number of days
        } catch (e: Exception) {
            30 // Default to 30 days if there's an error
        }
    }
}