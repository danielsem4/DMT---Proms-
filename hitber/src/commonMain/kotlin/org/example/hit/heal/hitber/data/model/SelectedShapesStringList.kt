package org.example.hit.heal.hitber.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SelectedShapesStringList(
    @SerialName("measureObject") val measureObject: Int = 0,
    @SerialName("value") val value: List<String> = emptyList(),
    @SerialName("DateTime") val dateTime: String = "2030-12-12 12:12:12.6")

