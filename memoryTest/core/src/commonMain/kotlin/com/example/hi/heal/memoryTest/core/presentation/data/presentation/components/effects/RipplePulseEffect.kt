package com.example.hi.heal.memoryTest.core.presentation.data.presentation.components.effects

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun RipplePulseEffect(
    modifier: Modifier = Modifier,
    color: Color = Color.Red,
    pulseCount: Int = 2 // количество волн
) {
    val infiniteTransition = rememberInfiniteTransition()

    // Для каждой волны создаём свою анимацию с разной задержкой
    val animations = List(pulseCount) { index ->
        val delayMillis = index * 600 // сдвиг между волнами
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1800, delayMillis = delayMillis, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        animations.forEach { anim ->
            val progress = anim.value
            val scale = 1f + progress * 1.5f
            val alpha = 1f - progress

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    }
                    .background(color.copy(alpha = 0.3f), shape = CircleShape)
            )
        }
    }
}