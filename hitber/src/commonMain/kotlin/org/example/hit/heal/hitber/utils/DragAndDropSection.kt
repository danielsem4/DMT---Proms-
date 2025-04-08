package org.example.hit.heal.hitber.utils

import androidx.compose.ui.geometry.Offset

fun isObjectInsideTargetArea(
    targetPosition: Offset,
    draggablePosition: Offset,
    targetSize: Pair<Float, Float>,
    draggableSize: Pair<Float, Float> = 0f to 0f,
    isCircle: Boolean,
    threshold: Float = 0f
): Boolean {
    val (targetWidth, targetHeight) = targetSize
    val (draggableWidth, draggableHeight) = draggableSize

    val targetLeft = targetPosition.x - threshold
    val targetTop = targetPosition.y - threshold
    val targetRight = targetPosition.x + targetWidth + threshold
    val targetBottom = targetPosition.y + targetHeight + threshold

    val draggableLeft: Float
    val draggableTop: Float
    val draggableRight: Float
    val draggableBottom: Float

    if (isCircle) {
        val draggableRadius = draggableWidth / 2
        draggableLeft = draggablePosition.x - draggableRadius
        draggableTop = draggablePosition.y - draggableRadius
        draggableRight = draggablePosition.x + draggableRadius
        draggableBottom = draggablePosition.y + draggableRadius
    } else {
        draggableLeft = draggablePosition.x
        draggableTop = draggablePosition.y
        draggableRight = draggableLeft + draggableWidth
        draggableBottom = draggableTop + draggableHeight
    }

    println("Is inside?targetLeft: $targetLeft, targetTop: $targetTop, targetRight: $targetRight, targetBottom: $targetBottom")
    println("Is inside?draggableLeft: $draggableLeft, draggableTop: $draggableTop, draggableRight: $draggableRight, draggableBottom: $draggableBottom")

    val isInside = draggableLeft >= targetLeft && draggableRight <= targetRight &&
            draggableTop >= targetTop && draggableBottom <= targetBottom

    println("✅ Is inside? $isInside")
    return isInside
}
