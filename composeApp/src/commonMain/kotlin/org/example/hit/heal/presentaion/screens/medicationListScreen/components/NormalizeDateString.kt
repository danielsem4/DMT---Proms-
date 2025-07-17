package org.example.hit.heal.presentaion.screens.medicationListScreen.components

fun normalizeDateString(dateString: String): String {
    return if (dateString.contains("-")) {
        val parts = dateString.split("-")
        if (parts.size == 3) {

            val (year, month, day) = parts
            "${day.toInt()}/${month.toInt()}/${year.toInt()}"
        } else {
            dateString
        }
    } else {
        dateString
    }
}