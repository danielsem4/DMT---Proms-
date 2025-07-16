package org.example.hit.heal.hitber.data.model

import core.data.model.MeasureObjectDouble
import core.data.model.MeasureObjectString
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TenthQuestionItem @OptIn(ExperimentalSerializationApi::class) constructor(
    val shape: MeasureObjectString? = null,
    val grade: MeasureObjectDouble? = null,

    @SerialName("imageUrl")
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val imageUrl: MeasureObjectString? = null
)




