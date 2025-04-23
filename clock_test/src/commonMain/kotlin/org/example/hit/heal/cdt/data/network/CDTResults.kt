package org.example.hit.heal.cdt.data.network

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
)