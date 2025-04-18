package org.example.hit.heal.evaluations.domain

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// ViewModel to Manage Evaluations
class EvaluationViewModel : ViewModel() {

    private val _evaluations = MutableStateFlow(
        listOf(
            Evaluation(
                id = 29,
                evaluationName = "test1",
                isPublic = true,
                isMultilingual = true,
                evaluationType = 0, // measurement
                evaluationObjects = listOf(
                    EvaluationObject(
                        id = 344,
                        evaluationQuestion = "Choose one option:",
                        evaluationScreen = 1,
                        evaluationOrder = 2,
                        returnValue = true,
                        numberOfValues = 1,
                        predefinedValues = true,
                        randomSelection = false,
                        orderImportant = false,
                        showIcon = false,
                        answer = "",
                        style = "radio styled",
                        isGrade = false,
                        evaluationId = 29,
                        objectType = 2,
                        language = 1,
                        availableValues = listOf(
                            EvaluationValue(418, "1", false, ""),
                            EvaluationValue(419, "2", false, ""),
                            EvaluationValue(420, "3", false, ""),
                            EvaluationValue(421, "4", false, ""),
                            EvaluationValue(422, "5", false, "")
                        )
                    ),
                    EvaluationObject(
                        id = 349,
                        evaluationQuestion = "choose multiple options:",
                        evaluationScreen = 2,
                        evaluationOrder = 1,
                        returnValue = true,
                        numberOfValues = 1,
                        predefinedValues = true,
                        randomSelection = false,
                        orderImportant = false,
                        showIcon = false,
                        answer = "",
                        style = "checkbox styled",
                        isGrade = false,
                        evaluationId = 29,
                        objectType = 4,
                        language = 1,
                        availableValues = listOf(
                            EvaluationValue(433, "1", false, ""),
                            EvaluationValue(434, "2", false, ""),
                            EvaluationValue(435, "3", false, ""),
                            EvaluationValue(436, "4", false, ""),
                            EvaluationValue(437, "5", false, "")
                        )
                    ),
                    EvaluationObject(
                        id = 350,
                        evaluationQuestion = "open question",
                        evaluationScreen = 3,
                        evaluationOrder = 1,
                        returnValue = true,
                        numberOfValues = 1,
                        predefinedValues = false,
                        randomSelection = false,
                        orderImportant = false,
                        showIcon = false,
                        answer = "",
                        style = "none",
                        isGrade = false,
                        evaluationId = 29,
                        objectType = 1,
                        language = 1,
                        availableValues = null
                    ),
                    EvaluationObject(
                        id = 351,
                        evaluationQuestion = "scale",
                        evaluationScreen = 4,
                        evaluationOrder = 1,
                        returnValue = true,
                        numberOfValues = 1,
                        predefinedValues = true,
                        randomSelection = false,
                        orderImportant = false,
                        showIcon = false,
                        answer = "",
                        style = "scale",
                        isGrade = false,
                        evaluationId = 29,
                        objectType = 34,
                        language = 1,
                        availableValues = listOf(
                            EvaluationValue(438, "1", false, ""),
                            EvaluationValue(439, "2", false, ""),
                            EvaluationValue(440, "3", false, ""),
                            EvaluationValue(441, "4", false, ""),
                            EvaluationValue(442, "5", false, "")
                        )
                    ),
                    EvaluationObject(
                        id = 352,
                        evaluationQuestion = "Choose one option:",
                        evaluationScreen = 5,
                        evaluationOrder = 1,
                        returnValue = true,
                        numberOfValues = 1,
                        predefinedValues = true,
                        randomSelection = false,
                        orderImportant = false,
                        showIcon = false,
                        answer = "",
                        style = "none",
                        isGrade = false,
                        evaluationId = 29,
                        objectType = 2,
                        language = 1,
                        availableValues = listOf(
                            EvaluationValue(443, "1", false, ""),
                            EvaluationValue(444, "2", false, ""),
                            EvaluationValue(445, "3", false, ""),
                            EvaluationValue(446, "4", false, ""),
                            EvaluationValue(447, "5", false, "")
                        )
                    )
                )
            ),
            Evaluation(
                id = 27,
                evaluationName = "test2",
                isPublic = true,
                isMultilingual = true,
                evaluationType = 0, // measurement
                evaluationObjects = listOf(
                    EvaluationObject(
                        id = 335,
                        evaluationQuestion = "choose multiple options:",
                        evaluationScreen = 1,
                        evaluationOrder = 1,
                        returnValue = true,
                        numberOfValues = 1,
                        predefinedValues = true,
                        randomSelection = false,
                        orderImportant = false,
                        showIcon = false,
                        answer = "",
                        style = "checkbox styled",
                        isGrade = false,
                        evaluationId = 27,
                        objectType = 4,
                        language = 1,
                        availableValues = listOf(
                            EvaluationValue(448, "1", false, ""),
                            EvaluationValue(449, "2", false, ""),
                            EvaluationValue(450, "3", false, ""),
                            EvaluationValue(451, "4", false, ""),
                            EvaluationValue(452, "5", false, "")
                        )
                    ),
                    EvaluationObject(
                        id = 339,
                        evaluationQuestion = "open question",
                        evaluationScreen = 2,
                        evaluationOrder = 1,
                        returnValue = true,
                        numberOfValues = 1,
                        predefinedValues = false,
                        randomSelection = false,
                        orderImportant = false,
                        showIcon = false,
                        answer = "",
                        style = "none",
                        isGrade = false,
                        evaluationId = 27,
                        objectType = 1,
                        language = 1,
                        availableValues = null
                    ),
                    EvaluationObject(
                        id = 353,
                        evaluationQuestion = "fill the Parkinson report",
                        evaluationScreen = 3,
                        evaluationOrder = 1,
                        returnValue = true,
                        numberOfValues = 1,
                        predefinedValues = false,
                        randomSelection = false,
                        orderImportant = false,
                        showIcon = false,
                        answer = "",
                        style = "none",
                        isGrade = false,
                        evaluationId = 27,
                        objectType = 11,
                        language = 1,
                        availableValues = null
                    ),
                    EvaluationObject(
                        id = 354,
                        evaluationQuestion = "on",
                        evaluationScreen = 3,
                        evaluationOrder = 2,
                        returnValue = true,
                        numberOfValues = 1,
                        predefinedValues = false,
                        randomSelection = false,
                        orderImportant = false,
                        showIcon = false,
                        answer = "",
                        style = "toggle button",
                        isGrade = false,
                        evaluationId = 27,
                        objectType = 5,
                        language = 1,
                        availableValues = null
                    ),
                    EvaluationObject(
                        id = 355,
                        evaluationQuestion = "off",
                        evaluationScreen = 3,
                        evaluationOrder = 2,
                        returnValue = true,
                        numberOfValues = 1,
                        predefinedValues = false,
                        randomSelection = false,
                        orderImportant = false,
                        showIcon = false,
                        answer = "",
                        style = "toggle button",
                        isGrade = false,
                        evaluationId = 27,
                        objectType = 5,
                        language = 1,
                        availableValues = null
                    ),
                    EvaluationObject(
                        id = 356,
                        evaluationQuestion = "",
                        evaluationScreen = 3,
                        evaluationOrder = 3,
                        returnValue = true,
                        numberOfValues = 1,
                        predefinedValues = true,
                        randomSelection = false,
                        orderImportant = false,
                        showIcon = false,
                        answer = "",
                        style = "none",
                        isGrade = false,
                        evaluationId = 27,
                        objectType = 4,
                        language = 1,
                        availableValues = listOf(
                            EvaluationValue(459, "Halluzination", false, ""),
                            EvaluationValue(460, "Dyskinesia", false, ""),
                            EvaluationValue(461, "Falls", false, "")
                        )
                    ),
                    EvaluationObject(
                        id = 357,
                        evaluationQuestion = "Dynamic",
                        evaluationScreen = 4,
                        evaluationOrder = 1,
                        returnValue = true,
                        numberOfValues = 1,
                        predefinedValues = false,
                        randomSelection = false,
                        orderImportant = false,
                        showIcon = false,
                        answer = "",
                        style = "none",
                        isGrade = false,
                        evaluationId = 27,
                        objectType = 11,
                        language = 1,
                        availableValues = null
                    ),
                    EvaluationObject(
                        id = 358,
                        evaluationQuestion = "scale",
                        evaluationScreen = 4,
                        evaluationOrder = 2,
                        returnValue = true,
                        numberOfValues = 1,
                        predefinedValues = true,
                        randomSelection = false,
                        orderImportant = false,
                        showIcon = false,
                        answer = "",
                        style = "slider",
                        isGrade = false,
                        evaluationId = 27,
                        objectType = 34,
                        language = 1,
                        availableValues = listOf(
                            EvaluationValue(456, "1", false, ""),
                            EvaluationValue(457, "2", false, ""),
                            EvaluationValue(458, "3", false, "")
                        )
                    ),
                    EvaluationObject(
                        id = 360,
                        evaluationQuestion = "draw",
                        evaluationScreen = 4,
                        evaluationOrder = 3,
                        returnValue = true,
                        numberOfValues = 1,
                        predefinedValues = false,
                        randomSelection = false,
                        orderImportant = false,
                        showIcon = false,
                        answer = "",
                        style = "none",
                        isGrade = false,
                        evaluationId = 27,
                        objectType = 21,
                        language = 1,
                        availableValues = null
                    )
                )
            ),
            Evaluation(
                id = 18,
                evaluationName = "test3",
                isPublic = false,
                isMultilingual = false,
                evaluationType = 0, // measurement
                evaluationObjects = listOf(
                    EvaluationObject(
                        id = 362,
                        evaluationQuestion = "Blood pressure",
                        evaluationScreen = 1,
                        evaluationOrder = 1,
                        returnValue = true,
                        numberOfValues = 1,
                        predefinedValues = false,
                        randomSelection = false,
                        orderImportant = false,
                        showIcon = false,
                        answer = "",
                        style = "none",
                        isGrade = false,
                        evaluationId = 18,
                        objectType = 11,
                        language = 1,
                        availableValues = null
                    ),
                    EvaluationObject(
                        id = 361,
                        evaluationQuestion = "Systolic:",
                        evaluationScreen = 1,
                        evaluationOrder = 2,
                        returnValue = true,
                        numberOfValues = 1,
                        predefinedValues = false,
                        randomSelection = false,
                        orderImportant = false,
                        showIcon = false,
                        answer = "",
                        style = "none",
                        isGrade = false,
                        evaluationId = 18,
                        objectType = 1,
                        language = 1,
                        availableValues = null
                    ),
                    EvaluationObject(
                        id = 363,
                        evaluationQuestion = "Diastolic:",
                        evaluationScreen = 1,
                        evaluationOrder = 2,
                        returnValue = true,
                        numberOfValues = 1,
                        predefinedValues = false,
                        randomSelection = false,
                        orderImportant = false,
                        showIcon = false,
                        answer = "",
                        style = "none",
                        isGrade = false,
                        evaluationId = 18,
                        objectType = 1,
                        language = 1,
                        availableValues = null
                    ),
                    EvaluationObject(
                        id = 364,
                        evaluationQuestion = "Sugar report:",
                        evaluationScreen = 2,
                        evaluationOrder = 1,
                        returnValue = true,
                        numberOfValues = 1,
                        predefinedValues = false,
                        randomSelection = false,
                        orderImportant = false,
                        showIcon = false,
                        answer = "",
                        style = "none",
                        isGrade = false,
                        evaluationId = 18,
                        objectType = 1,
                        language = 1,
                        availableValues = null
                    ),
                    EvaluationObject(
                        id = 365,
                        evaluationQuestion = "open question",
                        evaluationScreen = 3,
                        evaluationOrder = 1,
                        returnValue = true,
                        numberOfValues = 1,
                        predefinedValues = false,
                        randomSelection = false,
                        orderImportant = false,
                        showIcon = false,
                        answer = "",
                        style = "none",
                        isGrade = false,
                        evaluationId = 18,
                        objectType = 1,
                        language = 1,
                        availableValues = null
                    )
                )
            )
        )
    )

    val evaluations: StateFlow<List<Evaluation>> = _evaluations

    fun saveAnswer(objectId: Int, answer: String) {
        val updated = _evaluations.value.map { eval ->
            eval.copy(
                evaluationObjects = eval.evaluationObjects.map { obj ->
                    if (obj.id == objectId) obj.copy(answer = answer) else obj
                }
            )
        }
        _evaluations.value = updated
    }

}
