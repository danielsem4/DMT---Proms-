package org.example.hit.heal.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Medication(
    val Name: String,
    val id: Int,
    val notification : Boolean,
    val notificationType : String,

    val notificationTimesPerDay : Int,
    val notificationStartTime : String,
    val notificationSEndDate : String,
    val notificationStartDate : String,

    val notificationDaysWeek : List <String>,

    val report : Boolean,
    val reportDate : String,
    val reportTime : String



)