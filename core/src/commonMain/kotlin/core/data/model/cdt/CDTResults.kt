package core.data.model.cdt

import core.data.model.MeasureObjectInt
import core.data.model.MeasureObjectString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CDTResults(
    @SerialName("imageUrl") var imageUrl: MeasureObjectString = MeasureObjectString(186),
    @SerialName("timeChange1") var timeChange1: MeasureObjectString = MeasureObjectString(189),
    @SerialName("hourChange1") var hourChange1: MeasureObjectInt = MeasureObjectInt(190),
    @SerialName("minuteChange1") var minuteChange1: MeasureObjectInt = MeasureObjectInt(193),
    @SerialName("timeChange2") var timeChange2: MeasureObjectString = MeasureObjectString(191),
    @SerialName("hourChange2") var hourChange2: MeasureObjectInt = MeasureObjectInt(192),
    @SerialName("minuteChangeUrl2") var minuteChangeUrl2: MeasureObjectInt = MeasureObjectInt(194)
) {
    override fun toString(): String {
        return "CDTResults(" +
                "imageUrl=$imageUrl\n" +
                "timeChange1=$timeChange1\n" +
                "hourChange1=$hourChange1\n" +
                "minuteChange1=$minuteChange1\n" +
                "timeChange2=$timeChange2\n" +
                "hourChange2=$hourChange2\n" +
                "minuteChangeUrl2=$minuteChangeUrl2)"
    }
}