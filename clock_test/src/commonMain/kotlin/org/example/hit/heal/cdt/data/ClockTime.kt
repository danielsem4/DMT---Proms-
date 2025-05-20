package org.example.hit.heal.cdt.data

// Data class representing clock time
data class ClockTime(val hours: Int, val minutes: Int) {
    override fun toString(): String {
        return "$hours:${minutes.toString().padStart(2, '0')}"
    }
}