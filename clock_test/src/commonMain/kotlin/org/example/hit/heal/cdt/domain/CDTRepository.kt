package org.example.hit.heal.cdt.domain

import androidx.compose.ui.graphics.ImageBitmap
import org.example.hit.heal.cdt.data.network.CDTRequestBody
import org.example.hit.heal.cdt.data.network.CDTResults
import org.example.hit.heal.cdt.utils.network.EmptyResult
import org.example.hit.heal.cdt.utils.network.Error
import org.example.hit.heal.cdt.utils.network.NetworkError
import org.example.hit.heal.cdt.utils.network.Result

interface CDTRepository {
    suspend fun sendCDTRequest(): Result<String, Error>
    suspend fun uploadMeasureResponse(results: CDTRequestBody): EmptyResult<NetworkError>
    suspend fun uploadFileCog(imagePath: String):Result<String, Error>

    // State management
    fun getCDTResults(): CDTResults
    fun updateCDTResults(results: CDTResults)
    fun getClockDrawing(): ImageBitmap?
    fun setClockDrawing(image: ImageBitmap?)
    fun getTimeSpentDrawing(): Long
    fun setTimeSpentDrawing(time: Long)
    fun getTimeSpentSettingFirstClock(): Long
    fun setTimeSpentSettingFirstClock(time: Long)
    fun getTimeSpentSettingSecondClock(): Long
    fun setTimeSpentSettingSecondClock(time: Long)
    fun setCDTResults(newRes: CDTResults)
}