package com.example.new_memory_test.data.model

import core.data.model.MeasureObjectString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemoryQuestionPart1(
    @SerialName("item") val item: MeasureObjectString,
    @SerialName("place") val place: MeasureObjectString
)
