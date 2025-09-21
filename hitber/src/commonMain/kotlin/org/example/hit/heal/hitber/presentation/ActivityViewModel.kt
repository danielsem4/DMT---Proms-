package org.example.hit.heal.hitber.presentation

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.model.MeasureObjectBoolean
import core.data.model.MeasureObjectDouble
import core.data.model.MeasureObjectInt
import core.data.model.MeasureObjectString
import core.data.model.evaluation.Evaluation
import core.data.model.evaluation.EvaluationObject // <-- REQUIRED IMPORT
import core.data.storage.Storage
import core.domain.DataError
import core.domain.api.AppApi
import core.domain.onError
import core.domain.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import core.domain.use_case.BitmapToUploadUseCase
import org.example.hit.heal.hitber.data.model.CogData
import org.example.hit.heal.hitber.data.model.EighthQuestionItem
import org.example.hit.heal.hitber.data.model.FirstQuestion
import org.example.hit.heal.hitber.data.model.SecondQuestionItem
import org.example.hit.heal.hitber.data.model.SelectedShapesStringList
import org.example.hit.heal.hitber.data.model.ThirdQuestionItem
import core.domain.use_case.cdt.UploadFileUseCase
import core.domain.use_case.cdt.UploadTestResultsUseCase
import core.util.PrefKeys.clinicId
import core.util.PrefKeys.userId
import core.utils.getCurrentFormattedDateTime
import core.utils.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.hit.heal.hitber.data.model.SeventhQuestionItem
import org.example.hit.heal.hitber.data.model.SixthQuestionItem
import org.example.hit.heal.hitber.data.model.TenthQuestionItem

/**
 *
 */

class ActivityViewModel(
    private val uploadImageUseCase: UploadFileUseCase,
    private val uploadTestResultsUseCase: UploadTestResultsUseCase,
    private val bitmapToUploadUseCase: BitmapToUploadUseCase,
    private val api: AppApi,
    private val storage: Storage
) : ViewModel() {

    private var result: CogData = CogData()

    private val _uploadStatus = MutableStateFlow<Result<Unit>?>(null)
    val uploadStatus: StateFlow<Result<Unit>?> = _uploadStatus

    private val _hitBerTest = MutableStateFlow<Evaluation?>(null)
    val hitBerTest: StateFlow<Evaluation?> = _hitBerTest.asStateFlow()

    private val _capturedBitmap1 = MutableStateFlow<ImageBitmap?>(null)
    val capturedBitmap1: StateFlow<ImageBitmap?> = _capturedBitmap1.asStateFlow()

    private val _capturedBitmap2 = MutableStateFlow<ImageBitmap?>(null)
    val capturedBitmap2: StateFlow<ImageBitmap?> = _capturedBitmap2.asStateFlow()

    private val _capturedBitmap3 = MutableStateFlow<ImageBitmap?>(null)
    val capturedBitmap3: StateFlow<ImageBitmap?> = _capturedBitmap3.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var isUploadAllImagesFinished = 0

    /**
     * This map will store your server configuration, mapping labels like "city"
     * to the complete EvaluationObject which contains the dynamic ID.
     */
    private var dynamicObjectMap: Map<String, EvaluationObject> = emptyMap()

    /**
     * Helper function to safely get the dynamic ID from the map.
     * If the label isn't found, it returns the hardcoded fallback ID and logs a warning.
     */
    private fun getId(label: String, fallback: Int): Int {
        return dynamicObjectMap[label]?.id ?: run {
            println("Warning: Dynamic ID for label '$label' not found. Using fallback $fallback.")
            fallback
        }
    }

    /**
     * Fetches the evaluation and populates the dynamic ID map.
     */
    fun loadEvaluation(evaluationName: String) {
        viewModelScope.launch {
            val clinicId = storage.get(clinicId) ?: return@launch
            val patientId = storage.get(userId)?.toIntOrNull() ?: return@launch

            api.getSpecificEvaluation(clinicId, patientId, evaluationName)
                .onSuccess { fetched ->
                    _hitBerTest.value = fetched
                    // This is the crucial step: populate the map on success
                    fetched.measurement_objects?.let { setDynamicIds(it) }
                }
                .onError { error ->
                    println("Error fetching evaluation: $error")
                }
        }
    }

    fun setFirstQuestion(): ArrayList<Pair<ArrayList<String>, String>>? =
        hitBerTest.value?.measurement_objects
            ?.asSequence()
            ?.filter { it.measurement_screen == 1 }
            ?.map { mo ->
                val values: ArrayList<String> =
                    mo.available_values
                        ?.map { it.available_value ?: "" }
                        ?.toCollection(ArrayList()) ?: ArrayList()
                values to mo.object_label
            }
            ?.toCollection(arrayListOf())

    fun setFirstQuestionResults(value: ArrayList<core.data.model.MeasureObjectString>) {
        val firstScreenObjects = hitBerTest.value?.measurement_objects
            ?.filter { it.measurement_screen == 1 }
        value.forEachIndexed { index, measureObjStr ->
            measureObjStr.measureObject = firstScreenObjects?.get(index)?.id ?: 0
        }
        result.firstQuestion = value
    }

    /**
     * Stores the fetched list of objects into our ViewModel's map, indexed by label.
     */
    private fun setDynamicIds(measurementObjects: List<EvaluationObject>) {
        dynamicObjectMap = measurementObjects.associateBy { it.object_label }
        println("Dynamic Object Map successfully loaded.")
    }

    fun saveBitmap1(bitmap: ImageBitmap) {
        _capturedBitmap1.value = bitmap
    }

    fun saveBitmap2(bitmap: ImageBitmap) {
        _capturedBitmap2.value = bitmap
    }

    fun saveBitmap3(bitmap: ImageBitmap) {
        _capturedBitmap3.value = bitmap
    }

    fun setSecondQuestion(
        answers: List<Pair<Map<Int, String>, Int>>,
        date: String
    ) {
        val secondQuestionList = answers.map { (map, correctShapesCount) ->
            val shapesList = map.values.toList()

            SecondQuestionItem(
                selectedShapes = SelectedShapesStringList(
                    measureObject = getId("Second-Question-Shape", 110),
                    value = shapesList,
                    dateTime = date
                ),
                wrongShapes = MeasureObjectInt(
                    measureObject = getId("Second-Question-Wrong", 182),
                    value = 5 - correctShapesCount,
                    dateTime = date
                )
            )
        }

        result.secondQuestion = ArrayList(secondQuestionList)
    }


    fun setThirdQuestion(thirdQuestionAnswers: MutableList<Pair<Int, Int>>, date: String) {

        val thirdQuestionList = thirdQuestionAnswers.map { (answer, reactionTime) ->
            ThirdQuestionItem(
                number = MeasureObjectInt(
                    measureObject = getId("Third-Question-number", 111),
                    value = answer,
                    dateTime = date
                ),
                time = MeasureObjectInt(
                    measureObject = getId("Third-Question-time", 112),
                    value = reactionTime,
                    dateTime = date
                ),
                isPressed = MeasureObjectBoolean(
                    measureObject = getId("Third-Question-pressed", 113),
                    value = true,
                    dateTime = date
                )
            )
        }

        result.thirdQuestion = ArrayList(thirdQuestionList)
        println("thirdQuestion: ${result.thirdQuestion}")
    }

    fun setFourthQuestion(answers: List<String>, date: String) {
        val measureObjects = answers.mapIndexed { index, answer ->
            val label = "Fourth-Question-item-${index + 1}-answer"
            val fallbackId = 133 + (index * 2)

            MeasureObjectString(
                measureObject = getId(label, fallbackId),
                value = answer,
                dateTime = date
            )
        }

        result.fourthQuestion = ArrayList(measureObjects)
    }

    fun setSixthQuestion(
        fridgeOpened: Boolean,
        itemMovedCorrectly: Boolean,
        napkinPlacedCorrectly: Boolean,
        date: String
    ) {
        val sixthQuestionItem = SixthQuestionItem(
            fridgeOpened = MeasureObjectBoolean(
                measureObject = getId("Six-Question-FridgeOpen", 138),
                value = fridgeOpened,
                dateTime = date
            ),
            correctProductDragged = MeasureObjectBoolean(
                measureObject = getId("Six-Question-Product-dragged", 139),
                value = itemMovedCorrectly,
                dateTime = date
            ),
            placedOnCorrectNap = MeasureObjectBoolean(
                measureObject = getId("Six-Question-Product-nap", 140),
                value = napkinPlacedCorrectly,
                dateTime = date
            )
        )

        result.sixthQuestion = arrayListOf(sixthQuestionItem)
    }


    fun setSeventhQuestion(answer: Boolean, date: String) {

        val seventhQuestionItem = SeventhQuestionItem(
            isCorrect = MeasureObjectBoolean(
                measureObject = getId("Seven-Question-drag-and-drop", 141),
                value = answer,
                dateTime = date
            )
        )

        result.seventhQuestion = arrayListOf(seventhQuestionItem)
    }


    fun setEighthQuestion(
        answer: Boolean,
        date: String
    ) {

        val eighthQuestionItem = EighthQuestionItem(
            writtenSentence = MeasureObjectBoolean(
                measureObject = getId("Eight-Question-phrase", 142),
                value = answer,
                dateTime = date
            )
        )

        result.eighthQuestion = arrayListOf(eighthQuestionItem)
    }


    fun setNinthQuestion(
        answers: List<Pair<Map<Int, String>, Int>>,
        date: String
    ) {

        val ninthQuestionList = answers.map { (map, correctShapesCount) ->
            val shapesList = map.values.toList()
            SecondQuestionItem(
                selectedShapes = SelectedShapesStringList(
                    measureObject = getId("Nine-Question", 143),
                    value = shapesList,
                    dateTime = date
                ),
                wrongShapes = MeasureObjectInt(
                    measureObject = getId("wrong_shapes", 183),
                    value = 5 - correctShapesCount,
                    dateTime = date
                )
            )
        }

        result.ninthQuestion = ArrayList(ninthQuestionList)
    }


    fun setTenthQuestion(
        answer: ArrayList<Map<String, Double>>,
        date: String
    ) {

        val tenthQuestionList = answer.map { mapEntry ->
            val (shape, grade) = mapEntry.entries.first()
            TenthQuestionItem(
                shape = MeasureObjectString(
                    measureObject = getId("Tenth-Question-shape", 144),
                    value = shape,
                    dateTime = date
                ),
                grade = MeasureObjectDouble(
                    measureObject = getId("Tenth-Question-grade", 145),
                    value = grade,
                    dateTime = date
                ),
                imageUrl = null
            )
        }

        result.tenthQuestion = ArrayList(tenthQuestionList)
    }

    private val uploadScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun uploadImage(
        bitmap: ImageBitmap,
        date: String,
        currentQuestion: Int,
    ) {
        if (bitmap.width <= 1 || bitmap.height <= 1) {
            return
        }

        val imageByteArray = bitmap.toByteArray()

        uploadScope.launch {
            _isLoading.value = true
            try {
                val userId = storage.get(userId)!!
                val clinicId = storage.get(clinicId)!!
                val measurement = hitBerTest.value?.id ?: 19

                val imagePath = bitmapToUploadUseCase.buildPath(
                    clinicId = clinicId,
                    patientId = userId,
                    measurementId = measurement,
                    pathDate = date
                )

                val result = uploadImageUseCase.execute(
                    imagePath = imagePath,
                    bytes = imageByteArray,
                    clinicId = clinicId,
                    userId = userId
                )

                result.onSuccess {
                    // This function call is now refactored
                    saveUploadedImageUrl(currentQuestion, imagePath, date)

                    if (isUploadAllImagesFinished == 3) {
                        uploadEvaluationResults()
                        isUploadAllImagesFinished = 0
                    }

                }.onError { error ->
                    _uploadStatus.value = Result.failure(Exception(error.toString()))
                }
            } catch (e: Exception) {
                _uploadStatus.value = Result.failure(Exception(DataError.Remote.UNKNOWN.toString()))
            }
        }
    }

    /**
     * REFACTORED: This logic now maps question numbers to server LABELS, then looks up the ID.
     */
    private fun saveUploadedImageUrl(currentQuestion: Int?, uploadedUrl: String, date: String) {
        val imageLabels = mapOf(
            6 to "Six-Question-image",
            7 to "Seven-Question-image",
            10 to "image"
        )

        // Map of Question Number -> Fallback ID
        val fallbackIds = mapOf(
            6 to 201,
            7 to 203,
            10 to 205
        )

        // Get the correct label and fallback ID for the current question
        val label = imageLabels[currentQuestion] ?: "unknown_image"
        val fallback = fallbackIds[currentQuestion] ?: 0

        val image = MeasureObjectString(
            measureObject = getId(label, fallback), // Refactored logic
            value = uploadedUrl,
            dateTime = date
        )

        when (currentQuestion) {
            6 -> updateImageInQuestionList(
                list = result.sixthQuestion,
                createItem = { SixthQuestionItem(imageUrl = image) },
                updateItem = { it.copy(imageUrl = image) }
            )

            7 -> updateImageInQuestionList(
                list = result.seventhQuestion,
                createItem = { SeventhQuestionItem(imageUrl = image) },
                updateItem = { it.copy(imageUrl = image) }
            )

            10 -> {
                updateImageInQuestionList(
                    list = result.tenthQuestion,
                    createItem = { TenthQuestionItem(imageUrl = image) },
                    updateItem = { it.copy(imageUrl = image) },
                )
            }
        }
        isUploadAllImagesFinished++
    }

    /**
     * This helper function logic does not need to change.
     */
    private fun <T> updateImageInQuestionList(
        list: MutableList<T>,
        createItem: () -> T,
        updateItem: (T) -> T,
    ) {
        if (list.isEmpty()) {
            list.add(createItem())
        } else {
            val index = list.lastIndex
            list[index] = updateItem(list[index])
        }
    }

    /**
     * This function logic does not need to change.
     */
    private fun uploadEvaluationResults() {
        uploadScope.launch {
            try {
                result.patientId = storage.get(userId)!!.toInt()
                result.clinicId = storage.get(clinicId)!!
                result.measurement = hitBerTest.value?.id ?: 19
                result.date = getCurrentFormattedDateTime()

                val uploadResult = uploadTestResultsUseCase.execute(result, CogData.serializer())

                uploadResult.onSuccess {
                    _uploadStatus.value = Result.success(Unit)

                }.onError { error ->
                    _uploadStatus.value = Result.failure(Exception(error.toString()))
                }

            } catch (e: Exception) {
                _uploadStatus.value = Result.failure(Exception(DataError.Remote.UNKNOWN.toString()))
            } finally {
                _isLoading.value = false
            }
        }
    }
}