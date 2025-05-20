package org.example.hit.heal.hitber.presentation.buildShape

import androidx.lifecycle.ViewModel

class TenthQuestionViewModel : ViewModel() {
    val answer: ArrayList<Map<String, Double>> = ArrayList()

    fun tenthQuestionAnswer(shape: String, grade: Double) {
        answer.add(mapOf(shape to grade))
    }
}
