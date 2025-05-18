package presentation.endScreen

import BaseTabletScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.hit.heal.core.presentation.Colors.primaryColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.util.lerp
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.check
import dmt_proms.pass.generated.resources.end
import dmt_proms.pass.generated.resources.exit
import dmt_proms.pass.generated.resources.first_instructions_pass
import dmt_proms.pass.generated.resources.thanks_coffe
import dmt_proms.pass.generated.resources.thanks_vocal_pass

import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.components.AudioPlayingAnimation
import presentation.entryScreen.EntryViewModel
import presentation.result.ResultScreen
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class EndScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        val viewModel: EntryViewModel = koinViewModel()
        val isOverlayVisible by viewModel.isOverlayVisible.collectAsState()
        val audioString = stringResource(Res.string.thanks_vocal_pass)
        val isPlaying by viewModel.isPlaying.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.onPlayAudioRequested(audioString)
        }

        BaseTabletScreen(
            title = stringResource(Res.string.end),
            content = {
                AudioPlayingAnimation(isPlaying = isPlaying)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                            .border(
                                width = 2.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(top = 16.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.thanks_coffe),
                            color = primaryColor,
                            fontSize = 35.sp,
                            fontWeight = Bold,
                            lineHeight = 40.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    SuccessAnimation(modifier = Modifier.size(100.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {},
                            modifier = Modifier.width(150.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(stringResource(Res.string.exit), fontSize = 20.sp, fontWeight = Bold, color = Color.White)
                        }

                        Button(
                            onClick = { navigator?.push(ResultScreen()) },
                            modifier = Modifier.width(150.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("תוצאות", fontSize = 20.sp, fontWeight = Bold, color = Color.White)
                        }
                    }
                }
            }
        )

        if (isOverlayVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .clickable(
                        enabled = true,
                        onClick = { },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
            )
        }
    }
}

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

            // Draw white background circle
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

        // Checkmark icon
        if (tickAlpha.value > 0f) {
            Image(
                painter = painterResource(Res.drawable.check),
                contentDescription = "Success",
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