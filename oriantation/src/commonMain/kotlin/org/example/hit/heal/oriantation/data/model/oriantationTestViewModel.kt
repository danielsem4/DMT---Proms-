package org.example.hit.heal.oriantation.data.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import core.utils.toByteArray


class OrientationTestViewModel {
    var trialData by mutableStateOf(TrialData())
        private set
    var drawnShape: ByteArray? = null

    fun updateNumber(number: Int) {
        trialData.response.selectedNumber.value = number.toString()
    }

    fun updateSeason(season: String) {
        trialData.response.selectedSeasons.value = listOf(season)
    }


    fun updateDrawnShape(bitmap: ImageBitmap) {
        drawnShape = bitmap.toByteArray()

    }

    fun updateShapeResize(value: Boolean) {
        trialData.response.isTriangleSizeChanged.value = value
    }

    fun updateShapesDrag(result: Boolean) {
        trialData.response.isTriangleDragged.value = result
    }

    fun updateFeelingRate(rate: Int) {
        trialData.response.healthLevel.value = rate.toString()
    }

    fun getTestResults() = trialData.also {
        println("Test results: $it")
    }
}