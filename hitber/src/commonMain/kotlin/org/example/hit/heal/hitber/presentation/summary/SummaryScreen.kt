package org.example.hit.heal.hitber.presentation.summary

import TabletBaseScreen
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.check
import dmt_proms.hitber.generated.resources.summary_hitbear_exit
import dmt_proms.hitber.generated.resources.summary_hitbear_instructions1
import dmt_proms.hitber.generated.resources.summary_hitbear_instructions2
import dmt_proms.hitber.generated.resources.summary_hitbear_title
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.presentation.timeAndPlace.TimeAndPlace
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


class SummaryScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: ActivityViewModel = koinViewModel()
        val cogData by viewModel.cogData.collectAsState()

        TabletBaseScreen(
            title = stringResource(Res.string.summary_hitbear_title),
            onNextClick = { navigator?.push(TimeAndPlace()) },
            question = 10,
            buttonText = stringResource(Res.string.summary_hitbear_exit),
            buttonColor = primaryColor,
            content = {

                Column(
                    modifier = Modifier.fillMaxWidth().padding(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                    Text(stringResource(Res.string.summary_hitbear_instructions1), fontSize = 25.sp)
                    Text(stringResource(Res.string.summary_hitbear_instructions2), fontSize = 25.sp)
                    SuccessAnimation(modifier = Modifier.size(100.dp))

                }


                LaunchedEffect(Unit) {
                    cogData.firstQuestion?.let {
                        println("First Question: $it")
                    }

                    cogData.secondQuestion.forEachIndexed { i, item ->
                        println("Second Question #$i: $item")
                    }

                    cogData.thirdQuestion.forEachIndexed { i, item ->
                        println("Third Question #$i: $item")
                    }

                    cogData.fourthQuestion.forEachIndexed { i, item ->
                        println("Fourth Question #$i: $item")
                    }

                    cogData.fifthQuestion.forEachIndexed { i, item ->
                        println("Fifth Question #$i: $item")
                    }

                    cogData.sixthQuestion.forEachIndexed { i, item ->
                        println("Sixth Question #$i: $item")
                    }

                    cogData.seventhQuestion.forEachIndexed { i, item ->
                        println("Seventh Question #$i: $item")
                    }

                    cogData.eighthQuestion.forEachIndexed { i, item ->
                        println("Eighth Question #$i: $item")
                    }

                    cogData.ninthQuestion.forEachIndexed { i, item ->
                        println("Ninth Question #$i: $item")
                    }

                    cogData.tenthQuestion.forEachIndexed { i, item ->
                        println("Tenth Question #$i: $item")
                    }

                    println("Completed printing all cogData.")
                }



            })
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
                    .size(50.dp) // Size of the checkmark
                    .graphicsLayer {
                        alpha = tickAlpha.value
                        scaleX = tickAlpha.value
                        scaleY = tickAlpha.value
                    }
                    .clipToBounds()
                    .width(50.dp * tickAlpha.value) // Width starts from 0 and increases to full width
            )
        }

    }
    }
