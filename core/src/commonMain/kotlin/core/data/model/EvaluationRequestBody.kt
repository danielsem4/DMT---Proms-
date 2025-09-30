package core.data.model

import core.utils.getCurrentFormattedDateTime
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeasureObjectString(
    @SerialName("measureObject") var measureObject: Int = 0,
    @SerialName("value") var value: String = "",
    @SerialName("DateTime") var dateTime: String = getCurrentFormattedDateTime()
)

@Serializable
data class MeasureObjectInt(
    @SerialName("measureObject") var measureObject: Int = 0,
    @SerialName("value") var value: Int = 0,
    @SerialName("DateTime") var dateTime: String = getCurrentFormattedDateTime()
)

@Serializable
data class MeasureObjectBoolean(
    @SerialName("measureObject") var measureObject: Int = 0,
    @SerialName("value") var value: Boolean = false,
    @SerialName("DateTime") var dateTime: String = getCurrentFormattedDateTime()
)

@Serializable
data class MeasureObjectDouble(
    @SerialName("measureObject") var measureObject: Int = 0,
    @SerialName("value") var value: Double = 0.0,
    @SerialName("DateTime") var dateTime: String = getCurrentFormattedDateTime()
)

@Serializable
data class MeasureObjectStringArray(
    @SerialName("measureObject") var measureObject: Int = 0,
    @SerialName("value") var value: List<String> = listOf(),
    @SerialName("DateTime") var dateTime: String = getCurrentFormattedDateTime()
)