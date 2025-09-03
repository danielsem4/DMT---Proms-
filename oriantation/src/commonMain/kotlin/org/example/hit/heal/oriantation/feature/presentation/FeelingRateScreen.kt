package org.example.hit.heal.oriantation.feature.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.utils.RegisterBackHandler
import dmt_proms.oriantation.generated.resources.Res
import dmt_proms.oriantation.generated.resources.mid_pain_icon
import dmt_proms.oriantation.generated.resources.no_pain_icon
import dmt_proms.oriantation.generated.resources.pain_icon
import dmt_proms.oriantation.generated.resources.small_pain_icon
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
import org.koin.compose.viewmodel.koinViewModel

/**
 * Screen where user can rate their feeling on a scale from 0 to 10
 * with a slider and an image that changes according to the selected value
 */

class FeedbackScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: OrientationTestViewModel = koinViewModel()

        var progress by remember { mutableStateOf(0f) }
        var navigating by remember { mutableStateOf(false) } // prevent double pushes

        // Play state from VM (Flow/StateFlow)
        val isPlaying by viewModel.isPlayingAudio.collectAsState(initial = false)
        val audioText = stringResource(SetHealthRate)

        fun goNextOnce() {
            if (navigating) return
            navigating = true
            navigator.push(OrientationEndScreen())
        }

        // stop audio when leaving the screen
        DisposableEffect(Unit) {
            onDispose { viewModel.stopAudio() }
        }

        BaseScreen(
            config = ScreenConfig.TabletConfig,
            title = stringResource(vocalInstructions),
            onNextClick = { goNextOnce() }, // top-right Next: only navigate
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

                            // Listen button (plays via ViewModel)
                            RoundedButton(
                                text = stringResource(listening),
                                modifier = Modifier
                                    .width(180.dp)
                                    .height(56.dp),
                                onClick = { viewModel.onPlayAudio(audioText) },
                                enabled = !isPlaying
                            )
                            Text(if (isPlaying) "מנגן..." else "נגן")

                            Spacer(modifier = Modifier.height(100.dp))

                            // Slider
                            val availableValues = (0..10).map { it.toFloat() }
                            RoundedFilledSlider(
                                start = 0f,
                                end = 10f,
                                value = progress,
                                availableValues = availableValues,
                                startText = "0",
                                endText = "10",
                                onValueChanged = { newValue -> progress = newValue },
                                trackHeight = 48.dp,
                                cornerRadius = 16.dp,
                                showEdgeLabels = true
                            )

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
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Text(
                                    text = progress.toInt().toString(),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

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

                            // Bottom Next button: only navigate
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                RoundedButton(
                                    text = stringResource(NextText),
                                    modifier = Modifier
                                        .width(200.dp)
                                        .height(50.dp),
                                    onClick = { goNextOnce() },
                                    enabled = !navigating
                                )
                            }
                            Spacer(modifier = Modifier.height(32.dp))
                        }
                    }
                }
            }
        )
    }
}
