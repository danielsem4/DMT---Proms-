package org.example.hit.heal.oriantation.data.model

import androidx.compose.material.DrawerDefaults.shape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap

class OrientationTestViewModel {
    var state by mutableStateOf(OrientationTestState())
        private set

    fun updateNumber(number: Int) {
        state = state.copy(selectedNumber = number)
    }

    fun updateSeason(season: String) {
        state = state.copy(selectedSeason = season)
    }


    fun updateDrawnShape(bitmap: ImageBitmap) {
        state = state.copy(drawnShape = bitmap)
    }

    fun updateShapeResize(value: Boolean) {
        state = state.copy(shapeResizeValue = value)
    }

    fun updateShapesDrag(result: Boolean) {
        state = state.copy(shapesDragResult = result)
    }

    fun updateFeelingRate(rate: Int) {
        state = state.copy(feelingRate = rate)
    }

    fun getTestResults(): OrientationTestState {
        return state
    }
}