package org.example.hit.heal.evaluations

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap

interface DrawingCanvasController {
    fun drawPathsToBitmap(): ImageBitmap
    fun getPaths(): List<List<Offset>>
    fun undoLastStroke()
    fun clearCanvas()
}

