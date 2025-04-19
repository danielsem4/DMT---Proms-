package org.example.hit.heal.hitber.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeasureObjectString(
    @SerialName("measureObject") val measureObject: Int = 0,
    @SerialName("value") val value: String = "",
    @SerialName("DateTime") val dateTime: String = "2030-12-12 12:12:12.6"
)

@Serializable
data class MeasureObjectInt(
    @SerialName("measureObject") val measureObject: Int = 0,
    @SerialName("value") val value: Int = 0,
    @SerialName("DateTime") val dateTime: String = "2030-12-12 12:12:12.6"
)

@Serializable
data class MeasureObjectBoolean(
    @SerialName("measureObject") val measureObject: Int = 0,
    @SerialName("value") val value: Boolean = false,
    @SerialName("DateTime") val dateTime: String = "2030-12-12 12:12:12.6"
)

@Serializable
data class MeasureObjectDouble(
    @SerialName("measureObject") val measureObject: Int = 0,
    @SerialName("value") val value: Double = 0.0,
    @SerialName("DateTime") val dateTime: String = "2030-12-12 12:12:12.6"
)

@Serializable
data class MeasureObjectStringArray(
    @SerialName("measureObject") val measureObject: Int = 0,
    @SerialName("value") val value: List<String> = listOf(),
    @SerialName("DateTime") val dateTime: String = "2030-12-12 12:12:12.6"
)