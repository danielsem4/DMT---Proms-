package org.example.hit.heal.hitber.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun getNow(): String {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val date = now.date

    val time = buildString {
        append(if (now.hour < 10) "0${now.hour}" else now.hour)
        append(":")
        append(if (now.minute < 10) "0${now.minute}" else now.minute)
        append(":")
        append(if (now.second < 10) "0${now.second}" else now.second)
        append(".0")
    }

    return "$date $time"
}
