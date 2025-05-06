package org.example.hit.heal.cdt.data.network

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import core.utils.getCurrentFormattedDateTime

@Serializable
data class MeasureObjectString(
    @Required @SerialName("measureObject") val measureObject: Int = 0,
    @Required @SerialName("value") var value: String = "",
    @Required @SerialName("DateTime") var dateTime: String = getCurrentFormattedDateTime()
)

@Serializable
data class MeasureObjectInt(
    @Required @SerialName("measureObject") val measureObject: Int = 0,
    @Required @SerialName("value") var value: Int = 0,
    @Required @SerialName("DateTime") var dateTime: String = getCurrentFormattedDateTime()
)

@Serializable
data class MeasureObjectBoolean(
    @Required @SerialName("measureObject") val measureObject: Int = 0,
    @Required @SerialName("value") var value: Boolean = false,
    @Required @SerialName("DateTime") val dateTime: String = getCurrentFormattedDateTime()
)

@Serializable
data class MeasureObjectDouble(
    @Required @SerialName("measureObject") val measureObject: Int = 0,
    @Required @SerialName("value") var value: Double = 0.0,
    @Required @SerialName("DateTime") val dateTime: String = getCurrentFormattedDateTime()
)

@Serializable
data class MeasureObjectStringArray(
    @Required @SerialName("measureObject") val measureObject: Int = 0,
    @Required @SerialName("value") var value: List<String> = listOf(),
    @Required @SerialName("DateTime") val dateTime: String = getCurrentFormattedDateTime()
)