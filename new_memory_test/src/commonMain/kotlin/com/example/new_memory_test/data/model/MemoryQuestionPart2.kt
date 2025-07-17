package com.example.new_memory_test.data.model

import core.data.model.MeasureObjectString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemoryQuestionPart2(
    @SerialName("item") val item: MeasureObjectString,
    @SerialName("place") val place: MeasureObjectString,
    @SerialName("placeGrade") val placeGrade: MeasureObjectString,
    @SerialName("room") val room: MeasureObjectString
)