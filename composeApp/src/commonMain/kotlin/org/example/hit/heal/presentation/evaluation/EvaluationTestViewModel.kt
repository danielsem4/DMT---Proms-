package org.example.hit.heal.presentation.evaluation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.model.evaluation.Evaluation
import core.data.model.evaluation.EvaluationAnswer
import core.data.model.evaluation.EvaluationObject
import core.data.model.evaluation.EvaluationSettings
import core.data.model.evaluation.EvaluationValue
import core.data.storage.Storage
import core.domain.onError
import core.domain.onSuccess
import core.domain.use_case.cdt.UploadFileUseCase
import core.util.PrefKeys
import core.utils.getCurrentFormattedDateTime
import core.utils.toByteArray
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.listOf

// ViewModel to Manage Evaluations
class EvaluationTestViewModel(
    private val uploadImageUseCase: UploadFileUseCase,
    private val storage: Storage,
) : ViewModel() {

    private val _drawingPaths = mutableMapOf<Int, List<List<Offset>>>()
    val drawingPaths = _drawingPaths

//    private val _evaluations = MutableStateFlow(E)
//    val evaluations: StateFlow<List<Evaluation>> = _evaluations

    // Change _answers to be a MutableStateFlow holding a Map
    private val _answers = MutableStateFlow<Map<Int, EvaluationAnswer>>(emptyMap())
    val answers: StateFlow<Map<Int, EvaluationAnswer>> = _answers.asStateFlow()

    fun saveAnswer(objectId: Int, answer: EvaluationAnswer) {
        _answers.value = _answers.value.toMutableMap().apply { this[objectId] = answer }
    }

    fun getDrawingPaths(objectId: Int): List<List<Offset>> {
        return _drawingPaths[objectId] ?: emptyList()
    }

    fun saveDrawingPaths(objectId: Int, paths: List<List<Offset>>) {
        _drawingPaths[objectId] = paths
    }

    fun getAnswer(objectId: Int): EvaluationAnswer? {
        return _answers.value[objectId]
    }

    fun uploadDrawingImage(image: ImageBitmap, id: Int) {
        val imageByteArray = image.toByteArray()
        val measurement = 17
        val date = getCurrentFormattedDateTime()
        val version = 1
        val imgName = "evaluation_image.png"

        viewModelScope.launch {
            val userId = storage.get(PrefKeys.userId)!!
            val clinicId = storage.get(PrefKeys.clinicId)!!

            val imagePath = "clinics/$clinicId/patients/$userId/measurements/$measurement/" +
                    "$date/$version/$imgName"
            uploadImageUseCase.execute(imagePath, imageByteArray, clinicId, userId)
                .onSuccess {
                    println("Successfully uploaded Image")
                    saveAnswer(id, EvaluationAnswer.Image(imagePath))
                }
                .onError {
                    println("Error uploading image")
                    println(it)
                }
        }
    }

    fun submitEvaluation(id: Int, answers: Map<Int, EvaluationAnswer>) {
        println("Submit test: $id")
        println(answers)
//        TODO send to server
    }
}