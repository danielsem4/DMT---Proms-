package org.example.hit.heal.hitber.data.model
import core.data.model.MeasureObjectString
import core.utils.getCurrentFormattedDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CogData(
    @SerialName("measurement") var measurement: Int = 19,
    @SerialName("patient_id") var patientId: Int = 10,
    @SerialName("date") var date: String = getCurrentFormattedDateTime(),
    @SerialName("clinicId") var clinicId: Int = 3,
    @SerialName("firstQuestion") var firstQuestion: FirstQuestion = FirstQuestion(),
    @SerialName("secondQuestion") var secondQuestion: ArrayList<SecondQuestionItem> = arrayListOf(),
    @SerialName("thirdQuestion") var thirdQuestion: ArrayList<ThirdQuestionItem> = arrayListOf(),
    @SerialName("fourthQuestion") var fourthQuestion: ArrayList<MeasureObjectString> = arrayListOf(),
    @SerialName("fifthQuestion") var fifthQuestion: ArrayList<MeasureObjectString> = arrayListOf(),
    @SerialName("sixthQuestion") var sixthQuestion: ArrayList<SixthQuestionType> = arrayListOf(),
    @SerialName("seventhQuestion") var seventhQuestion: ArrayList<SeventhQuestionType> = arrayListOf(),
    @SerialName("eighthQuestion") var eighthQuestion: ArrayList<EighthQuestionItem> = arrayListOf(),
    @SerialName("ninthQuestion") var ninthQuestion: ArrayList<SecondQuestionItem> = arrayListOf(),
    @SerialName("tenthQuestion") var tenthQuestion: ArrayList<TenthQuestionType> = arrayListOf()
)

