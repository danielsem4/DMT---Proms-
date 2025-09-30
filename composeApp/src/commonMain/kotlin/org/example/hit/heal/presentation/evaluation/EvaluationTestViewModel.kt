package org.example.hit.heal.presentation.evaluation

import androidx.lifecycle.ViewModel
import core.data.model.MeasureObjectString
import core.data.model.evaluation.EvaluationAnswer
import core.data.model.evaluation.EvaluationRequestBody
import core.data.model.evaluation.toRawString
import core.data.storage.Storage
import core.domain.DataError
import core.domain.Result
import core.domain.map
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

class EvaluationTestViewModel(
    private val uploadImageUseCase: UploadFileUseCase,
    private val uploadTestResultsUseCase: UploadTestResultsUseCase,
    private val storage: Storage,
) : ViewModel() {

    private val _answers = MutableStateFlow<MutableMap<Int, EvaluationAnswer>>(mutableMapOf())
    val answers: StateFlow<Map<Int, EvaluationAnswer>> = _answers.asStateFlow()

    fun getAnswer(objectId: Int): EvaluationAnswer? {
        return _answers.value[objectId]
    }

    fun saveAnswer(objectId: Int, answer: EvaluationAnswer) {
        _answers.value = _answers.value.toMutableMap().apply {
            this[objectId] = answer
        }
    }

    suspend fun uploadDrawingImage(id: Int): Result<String, DataError> {
        val currentAnswer = getAnswer(id) as? EvaluationAnswer.Image
        val bitmap = currentAnswer?.bitmap

        if (bitmap == null) {
            println("No bitmap found for object id=$id")
            return Result.Error(DataError.Local.EMPTY_FILE)
        }

        val imageByteArray = bitmap.toByteArray()
        val date = getCurrentFormattedDateTime()
        val version = 1
        val imgName = "evaluation_image.png"

        val userId = storage.get(PrefKeys.userId)!!
        val clinicId = storage.get(PrefKeys.clinicId)!!

        val imagePath = "clinics/$clinicId/" +
                "patients/$userId/measurements/$id/" +
                "$date/$version/$imgName"

        val result = uploadImageUseCase.execute(imagePath, imageByteArray, clinicId, userId)

        result.onSuccess {
            println("Successfully uploaded Image - $it")

            // Update the answer map so UI updates too
            _answers.value = _answers.value.toMutableMap().apply {
                this[id] = EvaluationAnswer.Image(
                    bitmap = bitmap,
                    url = imagePath
                )
            }
        }.onError {
            println("Error uploading image: $it")
        }

        return result.map { imagePath }
    }


    suspend fun submitEvaluation(id: Int): Result<String, DataError> {
        println("Submit test: $id")
        println(answers)

        val patientId = storage.get(PrefKeys.userId)!!.toInt()
        val clinicId = storage.get(PrefKeys.clinicId)!!

        // Upload drawings first
        for ((objId, answer) in answers.value) {
            if (answer is EvaluationAnswer.Image && answer.url.isEmpty()) {
                when (val uploadResult = uploadDrawingImage(objId)) {
                    is Result.Error -> {
                        println("Failed to upload drawing for object $objId: ${uploadResult.error}")
                        return uploadResult.map { "" }
                    }

                    is Result.Success -> {
                        println("Uploaded drawing for object $objId: ${uploadResult.data}")
                    }
                }
            }
        }

        val results = answers.value.map { (objId, answer) ->
            MeasureObjectString(
                measureObject = objId,
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

        return uploadTestResultsUseCase.execute(body)
    }

}