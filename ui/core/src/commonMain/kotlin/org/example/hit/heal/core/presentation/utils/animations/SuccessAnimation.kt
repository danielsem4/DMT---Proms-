package org.example.hit.heal.core.presentation.utils.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import org.example.hit.heal.core.presentation.Resources.Icon.check
import org.example.hit.heal.core.presentation.Resources.String.dialogLike
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SuccessAnimation(modifier: Modifier = Modifier) {
    val circleScale = remember { Animatable(0f) }
    val morphProgress = remember { Animatable(0f) }
    val tickAlpha = remember { Animatable(0f) }
    val lineAlpha = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        circleScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(800, easing = FastOutSlowInEasing)
        )

        morphProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(800, easing = LinearEasing)
        )

        tickAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(500)
        )

        lineAlpha.animateTo(
            targetValue = 0f,
            animationSpec = tween(800, delayMillis = 500)
        )
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(100.dp)) {
            val center = this.center
            val baseRadius = size.minDimension / 2 * circleScale.value

            drawCircle(
                color = Color.White,
                radius = baseRadius,
                center = center
            )

            val lineCount = 12
            val angleStep = 360f / lineCount
            val innerRadius = baseRadius * 0.3f
            val outerRadius = baseRadius * 1.3f

            repeat(lineCount) { i ->
                val angleRad = (i * angleStep) * (PI / 180f)
                val direction = Offset(cos(angleRad).toFloat(), sin(angleRad).toFloat())

                val positionProgress = morphProgress.value
                val currentRadius = lerp(innerRadius, outerRadius, positionProgress)

                val progressSin = sin(PI * positionProgress).toFloat() // 0 ➝ 1 ➝ 0
                val lineLength = progressSin * baseRadius * 0.5f

                val start = center + direction * (currentRadius - lineLength / 2)
                val end = center + direction * (currentRadius + lineLength / 2)

                val stroke = lerp(10f, 3f, positionProgress)

                val elementAlpha = lineAlpha.value

                if (progressSin > 0.05f) {
                    drawLine(
                        color = Color(0xFF4CAF50).copy(alpha = elementAlpha),
                        start = start,
                        end = end,
                        strokeWidth = stroke,
                        cap = StrokeCap.Round
                    )
                } else {
                    drawCircle(
                        color = Color(0xFF4CAF50).copy(alpha = elementAlpha),
                        radius = 4f,
                        center = center + direction * currentRadius
                    )
                }
            }
        }

        if (tickAlpha.value > 0f) {
            Image(
                painter = painterResource(check),
                contentDescription = stringResource(dialogLike),
                modifier = Modifier
                    .size(50.dp)
                    .graphicsLayer {
                        alpha = tickAlpha.value
                        scaleX = tickAlpha.value
                        scaleY = tickAlpha.value
                    }
                    .clipToBounds()
                    .width(50.dp * tickAlpha.value)
            )
        }

    }
}