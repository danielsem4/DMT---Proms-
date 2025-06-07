package org.example.hit.heal.oriantation.feature.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DrawerDefaults.shape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dmt_proms.oriantation.generated.resources.Res
import dmt_proms.oriantation.generated.resources.set_health_rate
import org.example.hit.heal.core.presentation.TabletBaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.hitber.presentation.understanding.components.AudioPlayer
import org.jetbrains.compose.resources.stringResource
import kotlin.math.roundToInt

class FeedbackScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        var progress by remember { mutableStateOf(0f) }
        var barWidth by remember { mutableStateOf(1f) }

        val audioPlayer = remember { AudioPlayer() }
        val audioUrl = stringResource(Res.string.set_health_rate)
        TabletBaseScreen(
            title = "הוראות קוליות",
            question = 8,
            onNextClick = { /* TODO: Navigate to next screen */ },
            content = {
                Spacer(modifier = Modifier.height(32.dp))

                // Listen button
                RoundedButton(
                    text = "הקשב",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(180.dp)
                        .height(56.dp),
                    onclick = { // Play the audio when button is clicked
                        audioPlayer.play(audioUrl) {
                            // This will be called when audio playback completes
                            println("Audio playback completed")
                        }
                    }
                )



                Spacer(modifier = Modifier.height(32.dp))

                // Progress bar with numbers, and make it interactive
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    Text(
                        text = "10",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .padding(horizontal = 8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .border(1.dp, Color.White, RoundedCornerShape(16.dp))
                            .pointerInput(Unit) {
                                detectTapGestures { offset: Offset ->
                                    // Calculate value based on tap position (right to left)
                                    val value = ((barWidth - offset.x) / barWidth * 10f)
                                        .coerceIn(0f, 10f)
                                    progress = value.toInt().toFloat()
                                }
                            }
                            .onSizeChanged { barWidth = it.width.toFloat() }
                    ) {
                        if (progress > 0f) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(fraction = (10f - progress) / 10f)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(
                                        Brush.horizontalGradient(
                                            colors = listOf(Color(0xFFB6F055), Color(0xFFFFA726))
                                        )
                                    )
                            )
                        }
                    }
                    Text(
                        text = "0",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                // Show value and label if progress > 0
//                if (progress > 0f) {
                    Spacer(modifier = Modifier.height(15.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "מצב בריאותי טוב",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = progress.toInt().toString(),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }
//                } else {
//                    Spacer(modifier = Modifier.height(38.dp))
//                }
            }
        )
    }
}