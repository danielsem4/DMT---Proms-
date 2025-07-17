package com.example.new_memory_test.data.model

import core.data.model.MeasureObjectString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPlacement(
    @SerialName("activity") val activity: MeasureObjectString,
    @SerialName("day") val day: MeasureObjectString,
    @SerialName("time") val time: MeasureObjectString
)
