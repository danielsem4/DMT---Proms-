package com.clock.test.data

import com.clock.test.utils.getCurrentFormattedDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeasureObjectString(
    @SerialName("measureObject") val measureObject: Int = 0,
    @SerialName("value") var value: String = "",
    @SerialName("DateTime") var dateTime: String = getCurrentFormattedDateTime()
)

@Serializable
data class MeasureObjectInt(
    @SerialName("measureObject") val measureObject: Int = 0,
    @SerialName("value") var value: Int = 0,
    @SerialName("DateTime") var dateTime: String = getCurrentFormattedDateTime()
)

@Serializable
data class MeasureObjectBoolean(
    @SerialName("measureObject") val measureObject: Int = 0,
    @SerialName("value") var value: Boolean = false,
    @SerialName("DateTime") val dateTime: String = getCurrentFormattedDateTime()
)

@Serializable
data class MeasureObjectDouble(
    @SerialName("measureObject") val measureObject: Int = 0,
    @SerialName("value") var value: Double = 0.0,
    @SerialName("DateTime") val dateTime: String = getCurrentFormattedDateTime()
)

@Serializable
data class MeasureObjectStringArray(
    @SerialName("measureObject") val measureObject: Int = 0,
    @SerialName("value") var value: List<String> = listOf(),
    @SerialName("DateTime") val dateTime: String = getCurrentFormattedDateTime()
)