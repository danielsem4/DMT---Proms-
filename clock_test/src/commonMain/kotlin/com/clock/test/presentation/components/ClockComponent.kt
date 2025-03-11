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

// Data class representing clock time
data class ClockTime(val hours: Int, val minutes: Int) {
    override fun toString(): String {
        return "$hours:${minutes.toString().padStart(2, '0')}"
    }
}

@Composable
fun ClockComponent(
    modifier: Modifier = Modifier,
    initialTime: ClockTime = ClockTime(12, 0),
    onTimeChange: (ClockTime) -> Unit = {}
) {
    // Compute initial angles: adjust so 12:00 corresponds to -PI/2 radians.
    val hoursForAngle = if (initialTime.hours % 12 == 0) 12f else initialTime.hours % 12f
    val initialHourAngle = (hoursForAngle / 12f) * (2 * PI.toFloat()) - (PI.toFloat() / 2)
    val initialMinuteAngle = (initialTime.minutes / 60f) * (2 * PI.toFloat()) - (PI.toFloat() / 2)
    var hourAngle by remember { mutableStateOf(initialHourAngle) }
    var minuteAngle by remember { mutableStateOf(initialMinuteAngle) }
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
                            val minuteHandEnd = getHandEndPoint(
                                center,
                                minuteAngle,
                                size.width * minuteHandSizeRatio
                            )
                            val distanceToHour = (offset - hourHandEnd).getDistance()
                            val distanceToMinute = (offset - minuteHandEnd).getDistance()
                            if (distanceToHour < 50f) isDraggingHour = true
                            if (distanceToMinute < 50f) isDraggingMinute = true
                        },
                        onDrag = { change, _ ->
                            val center = Offset(size.width / 2f, size.height / 2f)
                            val angle = calculateAngle(center, change.position)
                            if (isDraggingHour) {
                                hourAngle = angle
                            }
                            if (isDraggingMinute) {
                                minuteAngle = angle
                            }
                            // Normalize angles: shift by PI/2 so that -PI/2 => 0
                            val normalizedHour =
                                ((hourAngle + (PI.toFloat() / 2) + 2 * PI.toFloat()) % (2 * PI.toFloat()))
                            val computedHours = (normalizedHour / (2 * PI.toFloat()) * 12f).toInt()
                            val normalizedMinute =
                                ((minuteAngle + (PI.toFloat() / 2) + 2 * PI.toFloat()) % (2 * PI.toFloat()))
                            val computedMinutes = (normalizedMinute / (2 * PI.toFloat()) * 60f).toInt()
                            
                            // אם השעה היא 0, נציג אותה כ-12
                            val finalHours = if (computedHours == 0) 12 else computedHours
                            
                            onTimeChange(ClockTime(finalHours, computedMinutes))
                        },
                        onDragEnd = {
                            isDraggingHour = false
                            isDraggingMinute = false
                        }
                    )
                }
        ) {
            // Draw clock face circle
            drawCircle(
                color = Colors.primaryColor,
                style = Stroke(width = 8.dp.toPx()),
                radius = size.minDimension / 2 - 8.dp.toPx()
            )

            // Draw numbers and dots on clock face
            for (i in 1..60) {
                if (i % 5 == 0) {
                    drawNumber((i / 5).toString(), i, textMeasurer)
                } else {
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
    drawLine(
        color = color,
        start = center,
        end = endPoint,
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
    val arrowPath = Path().apply {
        val arrowLength = if (isHourHand) 15.dp.toPx() else 20.dp.toPx()
        val angleInDegrees = angle * 180f / PI.toFloat()
        val leftPointAngle = (angleInDegrees - 150) * PI.toFloat() / 180f
        val rightPointAngle = (angleInDegrees + 150) * PI.toFloat() / 180f
        val leftPoint = Offset(
            endPoint.x + (arrowLength * cos(leftPointAngle)),
            endPoint.y + (arrowLength * sin(leftPointAngle))
        )
        val rightPoint = Offset(
            endPoint.x + (arrowLength * cos(rightPointAngle)),
            endPoint.y + (arrowLength * sin(rightPointAngle))
        )
        moveTo(endPoint.x, endPoint.y)
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
        x = center.x + (length * cos(angle)),
        y = center.y + (length * sin(angle))
    )
}

private fun calculateAngle(center: Offset, point: Offset): Float {
    return atan2(point.y - center.y, point.x - center.x)
}

