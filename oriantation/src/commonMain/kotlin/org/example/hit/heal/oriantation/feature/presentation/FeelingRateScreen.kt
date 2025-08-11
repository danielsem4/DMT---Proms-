package org.example.hit.heal.oriantation.feature.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.domain.use_case.PlayAudioUseCase
import core.utils.AudioPlayer
import org.example.hit.heal.core.presentation.utils.RegisterBackHandler
import dmt_proms.oriantation.generated.resources.Res
import dmt_proms.oriantation.generated.resources.mid_pain_icon
import dmt_proms.oriantation.generated.resources.no_pain_icon
import dmt_proms.oriantation.generated.resources.pain_icon
import dmt_proms.oriantation.generated.resources.small_pain_icon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Resources.String.NextText
import org.example.hit.heal.core.presentation.Resources.String.SetHealthRate
import org.example.hit.heal.core.presentation.Resources.String.feelingRateMid
import org.example.hit.heal.core.presentation.Resources.String.feelingRateNoPain
import org.example.hit.heal.core.presentation.Resources.String.feelingRatePain
import org.example.hit.heal.core.presentation.Resources.String.listening
import org.example.hit.heal.core.presentation.Resources.String.vocalInstructions
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.RoundedFilledSlider
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.oriantation.data.model.OrientationTestViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class FeedbackScreen(
    private val viewModel: OrientationTestViewModel
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var progress by remember { mutableStateOf(0f) }
        var isButtonEnabled by remember { mutableStateOf(true) }

        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()
        val successMessage = stringResource(Resources.String.sentSuccessfully)

        val audioPlayer = remember { AudioPlayer() }
        val playAudioUseCase = remember { PlayAudioUseCase(audioPlayer) }
        val audioUrl = stringResource(SetHealthRate)
        val isPlaying by playAudioUseCase.isPlaying.collectAsState()
        
        BaseScreen(
            config = ScreenConfig.TabletConfig,
            title = stringResource(vocalInstructions),
            onNextClick = {
                if (!isButtonEnabled) return@BaseScreen
                isButtonEnabled = false
                
                // Update the feeling rate in the view model
                viewModel.updateFeelingRate(progress.toInt())
                
                // Send data to server and navigate back to home
//                sendToServerAndNavigate(
//                    viewModel = viewModel,
//                    snackbarHostState = snackbarHostState,
//                    coroutineScope = coroutineScope,
//                    successMessage = successMessage,
//                    navigator = navigator
//                )
            },
            content = {
                Box(modifier = Modifier.fillMaxSize()) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

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
                                    coroutineScope.launch {
                                        playAudioUseCase.playAudio(audioUrl)
                                    }
                                },
                                enabled = !isPlaying
                            )
                            Text(if (isPlaying) "מנגן..." else "נגן")


                            // This will be called when audio playback completes
                            println("Audio playback completed")

                            Spacer(modifier = Modifier.height(100.dp))

                            // Use RoundedFilledSlider from core
                            val availableValues = (0..10).map { it.toFloat() }
                            RoundedFilledSlider(
                                start = 0f,
                                end = 10f,
                                value = progress,
                                availableValues = availableValues,
                                startText = "0",
                                endText = "10",
                                onValueChanged = { newValue ->
                                    progress = newValue
                                },
                                trackBrush = androidx.compose.ui.graphics.Brush.horizontalGradient(
                                    colors = listOf(
                                        androidx.compose.ui.graphics.Color(0xFFB6F055), // green
                                        androidx.compose.ui.graphics.Color(0xFFF7D155), // yellow
                                        androidx.compose.ui.graphics.Color(0xFFF7A155)  // orange
                                    )
                                ),
                                trackHeight = 48.dp,
                                cornerRadius = 16.dp,
                                showEdgeLabels = true
                            )

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
                                modifier = Modifier.size(150.dp)

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
                    }
                    SnackbarHost(
                        hostState = snackbarHostState,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            })
        RegisterBackHandler(this)
        {
            navigator?.popUntilRoot()
        }
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
                        navigator?.popUntilRoot()
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