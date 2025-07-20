package org.example.hit.heal.oriantation.feature.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.content.MediaType
import androidx.compose.foundation.content.MediaType.Companion.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DrawerDefaults.shape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Slider
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import dmt_proms.oriantation.generated.resources.close
import dmt_proms.oriantation.generated.resources.mid_pain_icon
import dmt_proms.oriantation.generated.resources.no_pain_icon
import dmt_proms.oriantation.generated.resources.pain_icon
import dmt_proms.oriantation.generated.resources.set_health_rate
import dmt_proms.oriantation.generated.resources.small_pain_icon
import dmt_proms.oriantation.generated.resources.winter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Resources.String.NextText
import org.example.hit.heal.core.presentation.Resources.String.feelingRateMid
import org.example.hit.heal.core.presentation.Resources.String.feelingRateNoPain
import org.example.hit.heal.core.presentation.Resources.String.feelingRatePain
import org.example.hit.heal.core.presentation.Resources.String.listening
import org.example.hit.heal.core.presentation.Resources.String.vocalInstructions
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.hitber.presentation.understanding.components.AudioPlayer
import org.example.hit.heal.oriantation.data.model.OrientationTestViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.roundToInt

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource

class FeedbackScreen(
    private val viewModel: OrientationTestViewModel
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var progress by remember { mutableStateOf(0f) }
        var barWidth by remember { mutableStateOf(0f) }
        var isButtonEnabled by remember { mutableStateOf(true) }

        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()
        val successMessage = stringResource(Resources.String.sentSuccessfully)

        val audioPlayer = remember { AudioPlayer() }
        val audioUrl = stringResource(Res.string.set_health_rate)
        
        BaseScreen(
            config = ScreenConfig.TabletConfig,
            title = stringResource(vocalInstructions),
            onNextClick = {
                if (!isButtonEnabled) return@BaseScreen
                isButtonEnabled = false
                
                // Update the feeling rate in the view model
                viewModel.updateFeelingRate(progress.toInt())
                
                // Send data to server and navigate back to home
                sendToServerAndNavigate(
                    viewModel = viewModel,
                    snackbarHostState = snackbarHostState,
                    coroutineScope = coroutineScope,
                    successMessage = successMessage,
                    navigator = navigator
                )
            },
            content = {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(32.dp))

                    // Listen button
                    RoundedButton(
                        text = stringResource(listening),
                        modifier = Modifier
                            .width(180.dp)
                            .height(56.dp),
                        onClick = { // Play the audio when button is clicked
                            audioPlayer.play(audioUrl) {
                                // This will be called when audio playback completes
                                println("Audio playback completed")
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(100.dp))

                    // Progress bar with numbers, and make it interactive
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    ) {
                        Text(
                            text = "0",
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
                                        val value = (offset.x / barWidth * 10f)
                                            .coerceIn(0f, 10f)
                                        progress = value
                                    }
                                }
                                .onSizeChanged { barWidth = it.width.toFloat() }
                        ) {
                            if (progress > 0f) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth(fraction = progress / 10f)
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
                            text = "10",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    // Show value and label if progress > 0
                    Spacer(modifier = Modifier.height(15.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = when {
                                progress <= 3f -> stringResource(feelingRatePain)
                                progress <= 6f -> stringResource(feelingRateMid)
                                else -> stringResource(feelingRateNoPain)
                            },
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = progress.toInt().toString(),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }


                    Spacer(modifier = Modifier.height(16.dp))

//                         Dynamic icon based on progress value
                        Image(
                            painter = painterResource(
                                when {
                                    progress <= 3f -> Res.drawable.pain_icon

                                    progress <= 5f -> Res.drawable.mid_pain_icon

                                    progress <= 8f -> Res.drawable.small_pain_icon

                                    else -> Res.drawable.no_pain_icon

                                }

                            ),
                            contentDescription = "Pain Icon",
                            modifier = Modifier.size(100.dp)

                        )

                    Spacer(modifier = Modifier.weight(1f))
                    // Navigation buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly
                    ) {
                        RoundedButton(
                            text = stringResource(NextText),
                            modifier = Modifier
                                .width(200.dp)
                                .height(50.dp),
                            onClick = {
                                if (!isButtonEnabled) return@RoundedButton
                                isButtonEnabled = false
                                
                                // Update the feeling rate in the view model
                                viewModel.updateFeelingRate(progress.toInt())
                                
                                // Send data to server and navigate back to home
                                sendToServerAndNavigate(
                                    viewModel = viewModel,
                                    snackbarHostState = snackbarHostState,
                                    coroutineScope = coroutineScope,
                                    successMessage = successMessage,
                                    navigator = navigator
                                )
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }

            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) { data ->
                    Snackbar(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = data.message,
                            fontSize = 32.sp
                        )
                    }
                }
            }
        )
    }

    private fun sendToServerAndNavigate(
        viewModel: OrientationTestViewModel,
        snackbarHostState: SnackbarHostState,
        coroutineScope: CoroutineScope,
        successMessage: String,
        navigator: cafe.adriel.voyager.navigator.Navigator
    ) {
        try {
            viewModel.sendToServer(
                onSuccess = {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(successMessage)
                        println("Sent successfully")
                        // Navigate back to home screen after successful upload
                    }
                },
                onFailure = { error ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(error.toString())
                    }
                }
            )
        } catch (error: Exception) {
            println(error.message)
            coroutineScope.launch {
                snackbarHostState.showSnackbar(error.toString())
            }
        }
    }
}