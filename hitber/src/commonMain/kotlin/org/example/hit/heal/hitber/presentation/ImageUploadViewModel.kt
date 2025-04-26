package org.example.hit.heal.hitber.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.suwasto.capturablecompose.rememberCaptureController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import okio.ByteString.Companion.decodeBase64
import org.example.hit.heal.hitber.data.ImageUploadRepository
import org.example.hit.heal.hitber.data.model.CogData
import org.example.hit.heal.hitber.data.model.MeasureObjectString
import org.example.hit.heal.hitber.data.model.SeventhQuestionType
import org.example.hit.heal.hitber.data.model.SixthQuestionType
import org.example.hit.heal.hitber.data.model.TenthQuestionType
import org.example.hit.heal.hitber.utils.getNow

class ImageUploadViewModel(
    private val repository: ImageUploadRepository
) : ViewModel() {

    private val _uploadState = MutableStateFlow<UploadState>(UploadState.Idle)
    private val _cogData = MutableStateFlow(CogData())

    fun uploadImage(
        base64: String,
        questionType: QuestionType
    ) {
        viewModelScope.launch {
            _uploadState.value = UploadState.Loading
            try {
                val fileBytes = base64.decodeBase64()?.toByteArray()

                val fileName = ".png" // שם קובץ אוטומטי

                val imageObject = MeasureObjectString(value = base64, dateTime = getNow())

                val response = fileBytes?.let {
                    repository.uploadImage(
                        fileBytes = it,
                        fileName = fileName,
                        clinicId = _cogData.value.clinicId,
                        patientId = _cogData.value.patientId,
                        measurement = _cogData.value.measurement.toString(),
                        date = imageObject.dateTime,
                        testVersion = ""
                    )
                }



                when (questionType) {
                    is QuestionType.SixthQuestion -> {
                        val currentList = ArrayList(_cogData.value.sixthQuestion)
                        val imageItem = SixthQuestionType.SixthQuestionImage(imageUrl = imageObject)
                        currentList.add(imageItem)
                        _cogData.value = _cogData.value.copy(sixthQuestion = currentList)
                    }
                    is QuestionType.SeventhQuestion -> {
                        val currentList = ArrayList(_cogData.value.seventhQuestion)
                        val imageItem = SeventhQuestionType.SeventhQuestionImage(imageUrl = imageObject)
                        currentList.add(imageItem)
                        _cogData.value = _cogData.value.copy(seventhQuestion = currentList)
                    }
                    is QuestionType.TenthQuestion -> {
                        val currentList = ArrayList(_cogData.value.tenthQuestion)
                        val imageItem = TenthQuestionType.TenthQuestionImage(imageUrl = imageObject)
                        currentList.add(imageItem)
                        _cogData.value = _cogData.value.copy(tenthQuestion = currentList)
                    }
                }




                _uploadState.value = response?.let { UploadState.Success(it) }!!

            } catch (e: Exception) {
                _uploadState.value = UploadState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class QuestionType {
    object SixthQuestion : QuestionType()
    object SeventhQuestion : QuestionType()
    object TenthQuestion : QuestionType()
}

sealed class UploadState {
    object Idle : UploadState()
    object Loading : UploadState()
    data class Success(val result: String) : UploadState()
    data class Error(val message: String) : UploadState()
}