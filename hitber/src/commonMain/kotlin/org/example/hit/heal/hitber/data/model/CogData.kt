package org.example.hit.heal.hitber.data.model
import core.data.model.MeasureObjectString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CogData(
    @SerialName("measurement") var measurement: Int = 0,
    @SerialName("patient_id") var patientId: Int = 0,
    @SerialName("date") var date: String = "2030-12-12 12:12:12.6",
    @SerialName("clinicId") var clinicId: Int = 0,
    @SerialName("firstQuestion") var firstQuestion: ArrayList<MeasureObjectString> = arrayListOf(),
    @SerialName("secondQuestion") var secondQuestion: ArrayList<SecondQuestionItem> = arrayListOf(),
    @SerialName("thirdQuestion") var thirdQuestion: ArrayList<ThirdQuestionItem> = arrayListOf(),
    @SerialName("fourthQuestion") var fourthQuestion: ArrayList<MeasureObjectString> = arrayListOf(),
    @SerialName("fifthQuestion") var fifthQuestion: ArrayList<MeasureObjectString> = arrayListOf(),
    @SerialName("sixthQuestion") var sixthQuestion: ArrayList<SixthQuestionItem> = arrayListOf(),
    @SerialName("seventhQuestion") var seventhQuestion: ArrayList<SeventhQuestionItem> = arrayListOf(),
    @SerialName("eighthQuestion") var eighthQuestion: ArrayList<EighthQuestionItem> = arrayListOf(),
    @SerialName("ninthQuestion") var ninthQuestion: ArrayList<SecondQuestionItem> = arrayListOf(),
    @SerialName("tenthQuestion") var tenthQuestion: ArrayList<TenthQuestionEntry> = arrayListOf()
)


