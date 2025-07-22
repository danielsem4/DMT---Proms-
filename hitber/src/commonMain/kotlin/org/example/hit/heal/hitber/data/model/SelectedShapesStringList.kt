package org.example.hit.heal.hitber.data.model

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SelectedShapesStringList @OptIn(ExperimentalSerializationApi::class) constructor(
    @SerialName("measureObject") val measureObject: Int = 0,
    @SerialName("value") @EncodeDefault val value: List<String> = emptyList(),
    @SerialName("DateTime") val dateTime: String = "2030-12-12 12:12:12.6")

