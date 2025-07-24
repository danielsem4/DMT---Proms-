package presentation.endScreen

import ToastMessage
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import core.utils.ObserveLifecycle
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.Resources.String.end
import org.example.hit.heal.core.presentation.Resources.String.exit
import org.example.hit.heal.core.presentation.Resources.String.thanksCoffe
import org.example.hit.heal.core.presentation.Resources.String.thanksVocalPass
import org.example.hit.heal.core.presentation.Sizes.radiusMd
import org.example.hit.heal.core.presentation.ToastType
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.example.hit.heal.core.presentation.utils.animations.AudioPlayingAnimation
import org.example.hit.heal.core.presentation.utils.animations.SuccessAnimation
import presentation.entryScreen.EntryViewModel

class EndScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: EntryViewModel = koinViewModel()
        val isOverlayVisible by viewModel.isOverlayVisible.collectAsState()
        val audioString = stringResource(thanksVocalPass)
        val isPlaying by viewModel.isPlaying.collectAsState()
        var toastMessage by remember { mutableStateOf<String?>(null) }
        var toastType by remember { mutableStateOf(ToastType.Normal) }

        BaseScreen(
            title = stringResource(end),
            config = ScreenConfig.TabletConfig,
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {


                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(45.dp)
                            ) {

                                // Animated visualization when audio is playing
                                AudioPlayingAnimation(isPlaying = isPlaying)

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            Color.White,
                                            shape = RoundedCornerShape(radiusMd)
                                        )
                                        .border(
                                            width = 2.dp,
                                            color = Color.LightGray,
                                            shape = RoundedCornerShape(radiusMd)
                                        )
                                ) {
                                    Text(
                                        text = stringResource(thanksCoffe),
                                        color = primaryColor,
                                        fontSize = 35.sp,
                                        fontWeight = Bold,
                                        lineHeight = 40.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                }
                                // Success checkmark animation
                                SuccessAnimation(modifier = Modifier.size(100.dp))
                            }
                        }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(80.dp),
                        modifier = Modifier.align(Alignment.BottomCenter)
                    ) {
                        RoundedButton(
                            text = stringResource(exit),
                            modifier = Modifier.align(Alignment.CenterHorizontally).width(200.dp),
                            onClick = {
                                navigator?.pop()
                            }
                        )

                        toastMessage?.let { msg ->
                            ToastMessage(
                                message = msg,
                                type = toastType,
                                alignUp = false,
                                onDismiss = { toastMessage = null }
                            )
                        }

                    }

                }
            })

        // Lifecycle observers to stop/play internal timers or checks
        ObserveLifecycle(
            onStop = {
                viewModel.stopAudio()
            },
            onStart = {
                viewModel.onPlayAudioRequested(audioString)
            }
        )


        // Shows a subtle dark overlay when audio is playing
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

        RegisterBackHandler(this)
        {
            navigator?.pop()
        }

    }
}


