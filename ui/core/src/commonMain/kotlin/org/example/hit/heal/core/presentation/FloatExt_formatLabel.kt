package org.example.hit.heal.core.presentation

fun Float.formatLabel(): String {
    return if (this % 1f == 0f) {
        this.toInt().toString()
    } else {
        val rounded = ((this * 10).toInt()).toFloat() / 10
        rounded.toString()
    }
}
