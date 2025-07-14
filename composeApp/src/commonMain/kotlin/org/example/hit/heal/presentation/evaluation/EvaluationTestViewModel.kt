package org.example.hit.heal.presentation.evaluation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.model.MeasureObjectString
import core.data.model.evaluation.EvaluationAnswer
import core.data.model.evaluation.toRawString
import core.data.storage.Storage
import core.domain.DataError
import core.domain.Result
import core.domain.onError
import core.domain.onSuccess
import core.domain.use_case.cdt.UploadFileUseCase
import core.domain.use_case.cdt.UploadTestResultsUseCase
import core.util.PrefKeys
import core.utils.getCurrentFormattedDateTime
import core.utils.toByteArray
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.hit.heal.evaluations.data.EvaluationRequestBody

class EvaluationTestViewModel(
    private val uploadImageUseCase: UploadFileUseCase,
    private val uploadTestResultsUseCase: UploadTestResultsUseCase,
    private val storage: Storage,
) : ViewModel() {

    private val _drawingPaths = mutableMapOf<Int, List<List<Offset>>>()
    val drawingPaths = _drawingPaths

    private val _answers = MutableStateFlow<Map<Int, EvaluationAnswer>>(emptyMap())
    val answers: StateFlow<Map<Int, EvaluationAnswer>> = _answers.asStateFlow()

    fun getAnswer(objectId: Int): EvaluationAnswer? {
        return _answers.value[objectId]
    }

    fun saveAnswer(objectId: Int, answer: EvaluationAnswer) {
        _answers.value = _answers.value.toMutableMap().apply { this[objectId] = answer }
    }

    fun uploadDrawingImage(image: ImageBitmap, id: Int) {
        val imageByteArray = image.toByteArray()
        val date = getCurrentFormattedDateTime()
        val version = 1
        val imgName = "evaluation_image.png"

        viewModelScope.launch {
            val userId = storage.get(PrefKeys.userId)!!
            val clinicId = storage.get(PrefKeys.clinicId)!!

            val imagePath = "clinics/$clinicId/" +
                    "patients/$userId/measurements/$id/" +
                    "$date/$version/$imgName"

            uploadImageUseCase.execute(imagePath, imageByteArray, clinicId, userId)
                .onSuccess { it: Int ->
                    println("Successfully uploaded Image - $it")
                    saveAnswer(id, EvaluationAnswer.Image(imagePath))
                }
                .onError {
                    println("Error uploading image")
                    println(it)
                }
        }
    }

    suspend fun submitEvaluation(id: Int): Result<String, DataError> {
        println("Submit test: $id")
        println(answers)

        val patientId = storage.get(PrefKeys.userId)!!.toInt()
        val clinicId = storage.get(PrefKeys.clinicId)!!

        val results = answers.value.map { (id, answer) ->
            if (answer is EvaluationAnswer.Image) {
//                    uploadDrawingImage(answer.url,id)
                //TODO: Upload image
            }
            MeasureObjectString(
                measureObject = id,
                value = answer.toRawString()
            )
        }
        val body = EvaluationRequestBody(
            measurement = id,
            patientId = patientId,
            date = getCurrentFormattedDateTime(),
            clinicId = clinicId,
            evaluationResults = results
        )
        println("EvaluationRequestBody")
        println(body)
        return uploadTestResultsUseCase.execute(body, EvaluationRequestBody.serializer())

    }
}