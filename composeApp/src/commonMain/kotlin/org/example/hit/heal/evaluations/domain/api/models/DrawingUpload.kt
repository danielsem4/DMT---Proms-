package org.example.hit.heal.evaluations.domain.api.models

data class DrawingUpload(
    val drawingId: String,
    val imageDataB64: String // Base64 encoded image data
)
