
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MemoryData(
    var measurement: Int = 0,
    @SerialName("patient_id") var patientId: Int = 0,
    var date: String = "",
    var clinicId: Int = 0,

    var MemoryQuestionPart1: MutableList<WrappedMemoryQuestionPart1> = mutableListOf(),
    var MemoryQuestionPart2: MutableList<WrappedMemoryQuestionPart2> = mutableListOf(),
    var MemoryQuestionPart3: MutableList<WrappedMemoryQuestionPart3> = mutableListOf(),

    var activitiesPlaced: MutableList<WrappedActivityPlacement> = mutableListOf(),
    var PhoneCallResult: MutableList<SimpleMeasureBoolean> = mutableListOf(),
    var notificationsCount: MutableList<SimpleMeasureString> = mutableListOf(),
    var successRateAfter: MutableList<SimpleMeasureString> = mutableListOf(),

    var imageUrl: MutableList<SimpleMeasureString> = mutableListOf(),
    var images1: MutableList<SimpleMeasureString> = mutableListOf(),
    var images2: MutableList<SimpleMeasureString> = mutableListOf(),
    var images3: MutableList<SimpleMeasureString> = mutableListOf()
)

@Serializable
data class WrappedMemoryQuestionPart1(
    @SerialName("MemoryQuestionPart1-Json")
    val json: MemoryQuestionPart1Json
)

@Serializable
data class MemoryQuestionPart1Json(
    @SerialName("DateTime") val dateTime: String,
    @SerialName("measureObject") val measureObject: Int,
    @SerialName("value") val value: MemoryQuestionPart1Value
)

@Serializable
data class MemoryQuestionPart1Value(
    @SerialName("Item") val item: String,
    @SerialName("Place") val place: String,
    @SerialName("Room") val room: String
)

@Serializable
data class WrappedMemoryQuestionPart2(
    @SerialName("MemoryQuestionPart2-Json")
    val json: MemoryQuestionPartNJson
)

@Serializable
data class WrappedMemoryQuestionPart3(
    @SerialName("MemoryQuestionPart3-Json")
    val json: MemoryQuestionPartNJson
)

@Serializable
data class MemoryQuestionPartNJson(
    @SerialName("DateTime") val dateTime: String,
    @SerialName("measureObject") val measureObject: Int,
    @SerialName("value") val value: MemoryQuestionPartNValue
)

@Serializable
data class MemoryQuestionPartNValue(
    @SerialName("Item") val item: String,
    @SerialName("Place") val place: String,
    @SerialName("PlaceGrade") val placeGrade: String,
    @SerialName("Room") val room: String
)

@Serializable
data class WrappedActivityPlacement(
    @SerialName("activitiesPlaced-Json")
    val json: ActivityPlacementJson
)

@Serializable
data class ActivityPlacementJson(
    @SerialName("DateTime") val dateTime: String,
    @SerialName("measureObject") val measureObject: Int,
    @SerialName("value") val value: ActivityPlacementValue
)

@Serializable
data class ActivityPlacementValue(
    @SerialName("activity") val activity: String,
    @SerialName("day") val day: String,
    @SerialName("time") val time: String
)

@Serializable
data class SimpleMeasureString(
    @SerialName("DateTime") val dateTime: String,
    @SerialName("measureObject") val measureObject: Int,
    @SerialName("value") val value: String
)

@Serializable
data class SimpleMeasureBoolean(
    @SerialName("DateTime") val dateTime: String,
    @SerialName("measureObject") val measureObject: Int,
    @SerialName("value") val value: Boolean
)