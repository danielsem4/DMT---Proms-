package core.data.model

import core.utils.getCurrentFormattedDateTime
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeasureObjectString(
    @Required @SerialName("measureObject") var measureObject: Int = 0,
    @Required @SerialName("value") var value: String = "",
    @Required @SerialName("DateTime") var dateTime: String = getCurrentFormattedDateTime()
)

@Serializable
data class MeasureObjectInt(
    @Required @SerialName("measureObject") var measureObject: Int = 0,
    @Required @SerialName("value") var value: Int = 0,
    @Required @SerialName("DateTime") var dateTime: String = getCurrentFormattedDateTime()
)

@Serializable
data class MeasureObjectBoolean(
    @Required @SerialName("measureObject") var measureObject: Int = 0,
    @Required @SerialName("value") var value: Boolean = false,
    @Required @SerialName("DateTime") var dateTime: String = getCurrentFormattedDateTime()
)

@Serializable
data class MeasureObjectDouble(
    @Required @SerialName("measureObject") var measureObject: Int = 0,
    @Required @SerialName("value") var value: Double = 0.0,
    @Required @SerialName("DateTime") var dateTime: String = getCurrentFormattedDateTime()
)

@Serializable
data class MeasureObjectStringArray(
    @Required @SerialName("measureObject") var measureObject: Int = 0,
    @Required @SerialName("value") var value: List<String> = listOf(),
    @Required @SerialName("DateTime") var dateTime: String = getCurrentFormattedDateTime()
)