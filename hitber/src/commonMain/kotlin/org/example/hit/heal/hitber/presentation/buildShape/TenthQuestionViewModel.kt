package org.example.hit.heal.hitber.presentation.buildShape

import androidx.lifecycle.ViewModel
import org.example.hit.heal.hitber.data.model.MeasureObjectDouble
import org.example.hit.heal.hitber.data.model.MeasureObjectString
import org.example.hit.heal.hitber.data.model.TenthQuestionType
import org.example.hit.heal.hitber.utils.getNow

class TenthQuestionViewModel : ViewModel() {
    val answer: ArrayList<Map<String, Double>> = ArrayList()

    fun tenthQuestionAnswer(shape: String, grade: Double) {
        answer.add(mapOf(shape to grade))
    }
}
