package org.example.hit.heal.domain

data class Medication(
    val name: String,
    val dosage: String,
    val frequency: String,
    val duration: String,
    val notes: String,
    val date: String,
    val id: Int,
    val status: String,
    val type: String,
    val dosageType: String
)
