package org.example.hit.heal.evaluations.domain.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class UploadResponse(
    val imageUrl: String
)
