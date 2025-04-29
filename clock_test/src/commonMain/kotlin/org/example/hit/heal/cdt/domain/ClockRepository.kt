package org.example.hit.heal.cdt.domain

import androidx.compose.ui.graphics.ImageBitmap
import org.example.hit.heal.cdt.data.network.CDTResults
import org.example.hit.heal.cdt.data.network.TransactionResult
import org.example.hit.heal.cdt.presentation.components.ClockTime
import org.example.hit.heal.cdt.utils.network.Error
import org.example.hit.heal.cdt.utils.network.Result

interface ClockRepository {
    suspend fun sendCDTRequest(): TransactionResult<String, Error>
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
    fun updateFirstClockTime(newTime: ClockTime)
    fun updateSecondClockTime(newTime: ClockTime)
}