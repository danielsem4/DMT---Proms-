package org.example.hit.heal.evaluations.domain

// ENUM for Frequency
enum class Frequency {
    HOURLY, DAILY, WEEKLY, MONTHLY
}

// Data Class for Evaluation Item
data class Evaluation(
    val name: String,
    val date: String,
    val frequency: Frequency
)