package com.clock.test.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun getCurrentFormattedDateTime(): String {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val milliseconds = now.nanosecond / 1_000_000
    val tenths = milliseconds / 100

    val day = now.dayOfMonth.toString().padStart(2, '0')
    val month = now.monthNumber.toString().padStart(2, '0')
    val year = now.year.toString()
    val hour = now.hour.toString().padStart(2, '0')
    val minute = now.minute.toString().padStart(2, '0')
    val second = now.second.toString().padStart(2, '0')

    // yyyy-MM-dd HH:mm:ss.S
    // "2030-12-12 12:12:12.6"
    return "$year-$month-$day $hour:$minute:$second.$tenths"
}