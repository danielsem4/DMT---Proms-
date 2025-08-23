package core.utils

import kotlinx.datetime.asClock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * Returns the current date and time formatted as a string in the format "yyyy-MM-dd HH:mm:ss.S".
 * The milliseconds are represented as tenths of a second.
 *
 * @return A string representing the current date and time in the specified format.
 */
@OptIn(ExperimentalTime::class)
fun getCurrentFormattedDateTime(now:LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())): String {


    val day = now.dayOfMonth.toString().padStart(2, '0')
    val month = now.monthNumber.toString().padStart(2, '0')
    val year = now.year.toString()
    val hour = now.hour.toString().padStart(2, '0')
    val minute = now.minute.toString().padStart(2, '0')
    val second = now.second.toString().padStart(2, '0')

    // yyyy-MM-dd HH-mm-ss
    // "2030-12-12-12-12-12"
    return "$year-$month-$day $hour:$minute:$second"
}