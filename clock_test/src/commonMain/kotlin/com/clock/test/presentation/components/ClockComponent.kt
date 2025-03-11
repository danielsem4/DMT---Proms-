package com.clock.test.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.hit.heal.core.presentation.Colors
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ClockComponent(
    modifier: Modifier = Modifier,
    onTimeChange: (hours: Float, minutes: Float) -> Unit = { _, _ -> }
) {
    var hourAngle by remember { mutableStateOf(0f) }
    var minuteAngle by remember { mutableStateOf(0f) }
    var isDraggingHour by remember { mutableStateOf(false) }
    var isDraggingMinute by remember { mutableStateOf(false) }
    val textMeasurer = rememberTextMeasurer()
    val hourHandSizeRatio = 0.2f
    val minuteHandSizeRatio = 0.4f

    Box(
        modifier = modifier.aspectRatio(1f)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            val center = Offset(size.width / 2f, size.height / 2f)
                            val hourHandEnd =
                                getHandEndPoint(center, hourAngle, size.width * hourHandSizeRatio)
                            val minuteHandEnd =
                                getHandEndPoint(
                                    center,
                                    minuteAngle,
                                    size.width * minuteHandSizeRatio
                                )

                            val distanceToHour = (offset - hourHandEnd).getDistance()
                            val distanceToMinute = (offset - minuteHandEnd).getDistance()

                            when {
                                distanceToHour < 50f -> isDraggingHour = true
                                distanceToMinute < 50f -> isDraggingMinute = true
                            }
                        },
                        onDrag = { change, _ ->
                            val center = Offset(size.width / 2f, size.height / 2f)
                            val dragPosition = change.position
                            val angle = calculateAngle(center, dragPosition)

                            when {
                                isDraggingHour -> {
                                    hourAngle = angle
                                    val hours = (hourAngle / (2 * PI) * 12).toFloat()
                                    onTimeChange(hours, (minuteAngle / (2 * PI) * 60).toFloat())
                                }

                                isDraggingMinute -> {
                                    minuteAngle = angle
                                    val minutes = (minuteAngle / (2 * PI) * 60).toFloat()
                                    onTimeChange((hourAngle / (2 * PI) * 12).toFloat(), minutes)
                                }
                            }
                        },
                        onDragEnd = {
                            isDraggingHour = false
                            isDraggingMinute = false
                        }
                    )
                }
        ) {
            // Draw clock circle
            drawCircle(
                color = Colors.primaryColor,
                style = Stroke(width = 8.dp.toPx()),
                radius = size.minDimension / 2 - 8.dp.toPx()
            )

            // Draw numbers and dots
            for (i in 1..60) {
                if (i % 5 == 0) {
                    // Draw main numbers
                    drawNumber((i / 5).toString(), i, textMeasurer)
                } else {
                    // Draw dots between numbers
                    drawDot(i)
                }
            }

            val center = Offset(size.width / 2, size.height / 2)

            // Draw hour hand with arrow
            drawClockHandWithArrow(
                center = center,
                angle = hourAngle,
                length = size.width * hourHandSizeRatio,
                strokeWidth = 12.dp.toPx(),
                color = Colors.primaryColor,
                isHourHand = true
            )

            // Draw minute hand with arrow
            drawClockHandWithArrow(
                center = center,
                angle = minuteAngle,
                length = size.width * minuteHandSizeRatio,
                strokeWidth = 8.dp.toPx(),
                color = Colors.primaryColor,
                isHourHand = false
            )

            // Draw center dot
            drawCircle(
                color = Colors.primaryColor,
                radius = 8.dp.toPx(),
                center = center
            )
        }
    }
}

private fun DrawScope.drawNumber(
    text: String,
    position: Int,
    textMeasurer: androidx.compose.ui.text.TextMeasurer
) {
    val center = Offset(size.width / 2, size.height / 2)
    val radius = size.minDimension / 2 - 40.dp.toPx()
    val angle = (position * 360f / 60f - 90f) * (PI / 180f)
    val numberPosition = Offset(
        x = center.x + (radius * cos(angle)).toFloat(),
        y = center.y + (radius * sin(angle)).toFloat()
    )

    val textLayoutResult = textMeasurer.measure(
        text = text,
        style = TextStyle(
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            color = Colors.primaryColor
        )
    )

    drawText(
        textLayoutResult = textLayoutResult,
        topLeft = Offset(
            numberPosition.x - textLayoutResult.size.width / 2,
            numberPosition.y - textLayoutResult.size.height / 2
        )
    )
}

private fun DrawScope.drawDot(position: Int) {
    val center = Offset(size.width / 2, size.height / 2)
    val radius = size.minDimension / 2 - 20.dp.toPx()
    val angle = (position * 360f / 60f - 90f) * (PI / 180f)
    val dotPosition = Offset(
        x = center.x + (radius * cos(angle)).toFloat(),
        y = center.y + (radius * sin(angle)).toFloat()
    )

    drawCircle(
        color = Colors.primaryColor,
        radius = 2.dp.toPx(),
        center = dotPosition
    )
}

private fun DrawScope.drawClockHandWithArrow(
    center: Offset,
    angle: Float,
    length: Float,
    strokeWidth: Float,
    color: Color,
    isHourHand: Boolean
) {
    val endPoint = getHandEndPoint(center, angle, length)

    // Draw main hand line
    drawLine(
        color = color,
        start = center,
        end = endPoint,
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )

    // Draw arrow
    val arrowPath = Path().apply {
        val arrowLength = if (isHourHand) 15.dp.toPx() else 20.dp.toPx()
        val arrowWidth = if (isHourHand) 10.dp.toPx() else 12.dp.toPx()
        val arrowOffset = 0.dp.toPx() // Remove offset to place arrow at the end point

        // Calculate the arrow base point at the end point
        val arrowBase = endPoint

        // Calculate arrow points
        val angleInDegrees = angle * 180f / PI.toFloat()
        val leftPointAngle = (angleInDegrees - 150) * PI.toFloat() / 180f
        val rightPointAngle = (angleInDegrees + 150) * PI.toFloat() / 180f

        val leftPoint = Offset(
            arrowBase.x + (arrowLength * cos(leftPointAngle)).toFloat(),
            arrowBase.y + (arrowLength * sin(leftPointAngle)).toFloat()
        )

        val rightPoint = Offset(
            arrowBase.x + (arrowLength * cos(rightPointAngle)).toFloat(),
            arrowBase.y + (arrowLength * sin(rightPointAngle)).toFloat()
        )

        // Draw the arrow
        moveTo(arrowBase.x, arrowBase.y)
        lineTo(leftPoint.x, leftPoint.y)
        lineTo(rightPoint.x, rightPoint.y)
        close()
    }

    drawPath(
        path = arrowPath,
        color = color
    )
}

private fun getHandEndPoint(center: Offset, angle: Float, length: Float): Offset {
    return Offset(
        x = center.x + (length * cos(angle)).toFloat(),
        y = center.y + (length * sin(angle)).toFloat()
    )
}

private fun calculateAngle(center: Offset, point: Offset): Float {
    return atan2(
        point.y - center.y,
        point.x - center.x
    )
}

