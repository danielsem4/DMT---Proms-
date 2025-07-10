package org.example.hit.heal.presentaion.screens.medicationReport

fun generateTimeSlots(): List<String> {
    val timeSlots = mutableListOf<String>()
    for (hour in 0..23) {
        for (minute in listOf(0, 15, 30, 45)) {
            for(second in 0..59) {
                val formattedTime =
                    "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}:${second.toString().padStart(2, '0')}"
                timeSlots.add(formattedTime)
            }
        }
    }
    return timeSlots
}