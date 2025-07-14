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

// ViewModel to Manage Evaluations
class EvaluationTestViewModel(
    private val uploadImageUseCase: UploadFileUseCase,
    private val storage: Storage,
) : ViewModel() {

    private val _drawingPaths = mutableMapOf<Int, List<List<Offset>>>()
    val drawingPaths = _drawingPaths

    private val _evaluations = MutableStateFlow(
        listOf(
            Evaluation(
                id = 29,
                measurement_name = "test1",
                display_as_module = true,
                is_multilingual = true,
                is_active = true,
                measurement_type = "measurement",
                measurement_settings = EvaluationSettings(
                    id = 1,
                    measurement_repeat_period = "Daily",
                    measurement_repeat_times = 1,
                    measurement_begin_time = "08:00",
                    measurement_last_time = "08:00",
                    measurement_end_time = null,
                    is_repetitive = false,
                    times_taken = 0,
                    patient = 0,
                    doctor = 0,
                    clinic = 0,
                    measurement = 0
                ),
                measurement_objects = listOf(
                    EvaluationObject(
                        id = 359,
                        object_label = "Dynamic",
                        measurement_screen = 4,
                        measurement_order = 1,
                        return_value = true,
                        number_of_values = 1,
                        predefined_values = false,
                        random_selection = false,
                        order_important = false,
                        show_icon = false,
                        answer = "",
                        style = "none",
                        is_grade = false,
                        measurement_id = 27,
                        object_type = 11,
                        language = 1,
                        available_values = null
                    ),
                    EvaluationObject(
                        id = 344,
                        object_label = "Choose one option:",
                        measurement_screen = 1,
                        measurement_order = 2,
                        return_value = true,
                        number_of_values = 1,
                        predefined_values = true,
                        random_selection = false,
                        order_important = false,
                        show_icon = false,
                        answer = "",
                        style = "radio styled",
                        is_grade = false,
                        measurement_id = 29,
                        object_type = 2,
                        language = 1,
                        available_values = listOf(
                            EvaluationValue(418, "1", false, "", 344),
                            EvaluationValue(419, "2", false, "", 344),
                            EvaluationValue(420, "3", false, "", 344),
                            EvaluationValue(421, "4", false, "", 344),
                            EvaluationValue(422, "5", false, "", 344)
                        )
                    ),
                    EvaluationObject(
                        id = 349,
                        object_label = "choose multiple options:",
                        measurement_screen = 2,
                        measurement_order = 1,
                        return_value = true,
                        number_of_values = 1,
                        predefined_values = true,
                        random_selection = false,
                        order_important = false,
                        show_icon = false,
                        answer = "",
                        style = "checkbox styled",
                        is_grade = false,
                        measurement_id = 29,
                        object_type = 4,
                        language = 1,
                        available_values = listOf(
                            EvaluationValue(433, "1", false, "", 349),
                            EvaluationValue(434, "2", false, "", 349),
                            EvaluationValue(435, "3", false, "", 349),
                            EvaluationValue(436, "4", false, "", 349),
                            EvaluationValue(437, "5", false, "", 349)
                        )
                    ),
                    EvaluationObject(
                        id = 350,
                        object_label = "open question",
                        measurement_screen = 3,
                        measurement_order = 1,
                        return_value = true,
                        number_of_values = 1,
                        predefined_values = false,
                        random_selection = false,
                        order_important = false,
                        show_icon = false,
                        answer = "",
                        style = "none",
                        is_grade = false,
                        measurement_id = 29,
                        object_type = 1,
                        language = 1,
                        available_values = null
                    ),
                    EvaluationObject(
                        id = 351,
                        object_label = "scale",
                        measurement_screen = 4,
                        measurement_order = 1,
                        return_value = true,
                        number_of_values = 1,
                        predefined_values = true,
                        random_selection = false,
                        order_important = false,
                        show_icon = false,
                        answer = "",
                        style = "scale",
                        is_grade = false,
                        measurement_id = 29,
                        object_type = 34,
                        language = 1,
                        available_values = listOf(
                            EvaluationValue(438, "1", false, "", 351),
                            EvaluationValue(439, "2", false, "", 351),
                            EvaluationValue(440, "3", false, "", 351),
                            EvaluationValue(441, "4", false, "", 351),
                            EvaluationValue(442, "5", false, "", 351)
                        )
                    ),
                    EvaluationObject(
                        id = 352,
                        object_label = "Choose one option:",
                        measurement_screen = 5,
                        measurement_order = 1,
                        return_value = true,
                        number_of_values = 1,
                        predefined_values = true,
                        random_selection = false,
                        order_important = false,
                        show_icon = false,
                        answer = "",
                        style = "none",
                        is_grade = false,
                        measurement_id = 29,
                        object_type = 2,
                        language = 1,
                        available_values = listOf(
                            EvaluationValue(443, "1", false, "", 352),
                            EvaluationValue(444, "2", false, "", 352),
                            EvaluationValue(445, "3", false, "", 352),
                            EvaluationValue(446, "4", false, "", 352),
                            EvaluationValue(447, "5", false, "", 352)
                        )
                    )
                )
            ),
            Evaluation(
                id = 27,
                measurement_name = "test2",
                display_as_module = true,
                is_multilingual = true,
                is_active = true,
                measurement_type = "measurement",
                measurement_settings = EvaluationSettings(
                    id = 2,
                    measurement_repeat_period = "Weekly",
                    measurement_repeat_times = 1,
                    measurement_begin_time = "10:00",
                    measurement_last_time = "10:00",
                    measurement_end_time = null,
                    is_repetitive = false,
                    times_taken = 0,
                    patient = 0,
                    doctor = 0,
                    clinic = 0,
                    measurement = 0
                ),
                measurement_objects = listOf(
                    EvaluationObject(
                        id = 335,
                        object_label = "choose multiple options:",
                        measurement_screen = 1,
                        measurement_order = 1,
                        return_value = true,
                        number_of_values = 1,
                        predefined_values = true,
                        random_selection = false,
                        order_important = false,
                        show_icon = false,
                        answer = "",
                        style = "checkbox styled",
                        is_grade = false,
                        measurement_id = 27,
                        object_type = 4,
                        language = 1,
                        available_values = listOf(
                            EvaluationValue(448, "1", false, "", 335),
                            EvaluationValue(449, "2", false, "", 335),
                            EvaluationValue(450, "3", false, "", 335),
                            EvaluationValue(451, "4", false, "", 335),
                            EvaluationValue(452, "5", false, "", 335)
                        )
                    ),
                    EvaluationObject(
                        id = 339,
                        object_label = "open question",
                        measurement_screen = 2,
                        measurement_order = 1,
                        return_value = true,
                        number_of_values = 1,
                        predefined_values = false,
                        random_selection = false,
                        order_important = false,
                        show_icon = false,
                        answer = "",
                        style = "none",
                        is_grade = false,
                        measurement_id = 27,
                        object_type = 1,
                        language = 1,
                        available_values = null
                    ),
                    EvaluationObject(
                        id = 353,
                        object_label = "fill the Parkinson report",
                        measurement_screen = 3,
                        measurement_order = 1,
                        return_value = true,
                        number_of_values = 1,
                        predefined_values = false,
                        random_selection = false,
                        order_important = false,
                        show_icon = false,
                        answer = "",
                        style = "none",
                        is_grade = false,
                        measurement_id = 27,
                        object_type = 11,
                        language = 1,
                        available_values = null
                    ),
                    EvaluationObject(
                        id = 354,
                        object_label = "on",
                        measurement_screen = 3,
                        measurement_order = 2,
                        return_value = true,
                        number_of_values = 1,
                        predefined_values = false,
                        random_selection = false,
                        order_important = false,
                        show_icon = false,
                        answer = "",
                        style = "toggle button",
                        is_grade = false,
                        measurement_id = 27,
                        object_type = 5,
                        language = 1,
                        available_values = null
                    ),
                    EvaluationObject(
                        id = 355,
                        object_label = "off",
                        measurement_screen = 3,
                        measurement_order = 2,
                        return_value = true,
                        number_of_values = 1,
                        predefined_values = false,
                        random_selection = false,
                        order_important = false,
                        show_icon = false,
                        answer = "",
                        style = "toggle button",
                        is_grade = false,
                        measurement_id = 27,
                        object_type = 5,
                        language = 1,
                        available_values = null
                    ),
                    EvaluationObject(
                        id = 356,
                        object_label = "",
                        measurement_screen = 3,
                        measurement_order = 3,
                        return_value = true,
                        number_of_values = 1,
                        predefined_values = true,
                        random_selection = false,
                        order_important = false,
                        show_icon = false,
                        answer = "",
                        style = "none",
                        is_grade = false,
                        measurement_id = 27,
                        object_type = 4,
                        language = 1,
                        available_values = listOf(
                            EvaluationValue(459, "Halluzination", false, "", 356),
                            EvaluationValue(460, "Dyskinesia", false, "", 356),
                            EvaluationValue(461, "Falls", false, "", 356)
                        )
                    ),
                    EvaluationObject(
                        id = 357,
                        object_label = "Dynamic",
                        measurement_screen = 4,
                        measurement_order = 1,
                        return_value = true,
                        number_of_values = 1,
                        predefined_values = false,
                        random_selection = false,
                        order_important = false,
                        show_icon = false,
                        answer = "",
                        style = "none",
                        is_grade = false,
                        measurement_id = 27,
                        object_type = 11,
                        language = 1,
                        available_values = null
                    ),
                    EvaluationObject(
                        id = 358,
                        object_label = "scale",
                        measurement_screen = 4,
                        measurement_order = 2,
                        return_value = true,
                        number_of_values = 1,
                        predefined_values = true,
                        random_selection = false,
                        order_important = false,
                        show_icon = false,
                        answer = "",
                        style = "slider",
                        is_grade = false,
                        measurement_id = 27,
                        object_type = 34,
                        language = 1,
                        available_values = listOf(
                            EvaluationValue(456, "1", false, "", 358),
                            EvaluationValue(457, "2", false, "", 358),
                            EvaluationValue(458, "3", false, "", 358)
                        )
                    ),
                    EvaluationObject(
                        id = 360,
                        object_label = "draw",
                        measurement_screen = 4,
                        measurement_order = 3,
                        return_value = true,
                        number_of_values = 1,
                        predefined_values = false,
                        random_selection = false,
                        order_important = false,
                        show_icon = false,
                        answer = "",
                        style = "none",
                        is_grade = false,
                        measurement_id = 27,
                        object_type = 21,
                        language = 1,
                        available_values = null
                    )
                )
            ),
            Evaluation(
                id = 18,
                measurement_name = "test3",
                display_as_module = false,
                is_multilingual = false,
                is_active = true,
                measurement_type = "measurement",
                measurement_settings = EvaluationSettings(
                    id = 3,
                    measurement_repeat_period = "Monthly",
                    measurement_repeat_times = 1,
                    measurement_begin_time = "09:00",
                    measurement_last_time = "09:00",
                    measurement_end_time = null,
                    is_repetitive = false,
                    times_taken = 0,
                    patient = 0,
                    doctor = 0,
                    clinic = 0,
                    measurement = 0
                ),
                measurement_objects = listOf(
                    EvaluationObject(
                        id = 362,
                        object_label = "Blood pressure",
                        measurement_screen = 1,
                        measurement_order = 1,
                        return_value = true,
                        number_of_values = 1,
                        predefined_values = false,
                        random_selection = false,
                        order_important = false,
                        show_icon = false,
                        answer = "",
                        style = "none",
                        is_grade = false,
                        measurement_id = 18,
                        object_type = 11,
                        language = 1,
                        available_values = null
                    ),
                    EvaluationObject(
                        id = 361,
                        object_label = "Systolic:",
                        measurement_screen = 1,
                        measurement_order = 2,
                        return_value = true,
                        number_of_values = 1,
                        predefined_values = false,
                        random_selection = false,
                        order_important = false,
                        show_icon = false,
                        answer = "",
                        style = "none",
                        is_grade = false,
                        measurement_id = 18,
                        object_type = 1,
                        language = 1,
                        available_values = null
                    ),
                    EvaluationObject(
                        id = 363,
                        object_label = "Diastolic:",
                        measurement_screen = 1,
                        measurement_order = 2,
                        return_value = true,
                        number_of_values = 1,
                        predefined_values = false,
                        random_selection = false,
                        order_important = false,
                        show_icon = false,
                        answer = "",
                        style = "none",
                        is_grade = false,
                        measurement_id = 18,
                        object_type = 1,
                        language = 1,
                        available_values = null
                    ),
                    EvaluationObject(
                        id = 364,
                        object_label = "Sugar report:",
                        measurement_screen = 2,
                        measurement_order = 1,
                        return_value = true,
                        number_of_values = 1,
                        predefined_values = false,
                        random_selection = false,
                        order_important = false,
                        show_icon = false,
                        answer = "",
                        style = "none",
                        is_grade = false,
                        measurement_id = 18,
                        object_type = 1,
                        language = 1,
                        available_values = null
                    ),
                    EvaluationObject(
                        id = 365,
                        object_label = "open question",
                        measurement_screen = 3,
                        measurement_order = 1,
                        return_value = true,
                        number_of_values = 1,
                        predefined_values = false,
                        random_selection = false,
                        order_important = false,
                        show_icon = false,
                        answer = "",
                        style = "none",
                        is_grade = false,
                        measurement_id = 18,
                        object_type = 1,
                        language = 1,
                        available_values = null
                    )
                )
            )
        )
    )
    val evaluations: StateFlow<List<Evaluation>> = _evaluations

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