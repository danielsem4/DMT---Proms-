package presentation.endScreen

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
import androidx.compose.ui.text.style.TextAlign
import core.utils.ObserveLifecycle
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.Resources.String.end
import org.example.hit.heal.core.presentation.Resources.String.exit
import org.example.hit.heal.core.presentation.Resources.String.next
import org.example.hit.heal.core.presentation.Resources.String.thanksCoffe
import org.example.hit.heal.core.presentation.Resources.String.thanksVocalPass
import org.example.hit.heal.core.presentation.Sizes.radiusMd
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

        ObserveLifecycle(
            onStop = {
                viewModel.stopAudio()
            },
            onStart = {
                viewModel.onPlayAudioRequested(audioString)
            }
        )

        BaseScreen(
            title = stringResource(end),
            config = ScreenConfig.TabletConfig,
            content = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(45.dp)
                ) {

                    AudioPlayingAnimation(isPlaying = isPlaying)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth().weight(1f)
                            .background(Color.White, shape = RoundedCornerShape(radiusMd))
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

                    SuccessAnimation(modifier = Modifier.size(100.dp))

                        RoundedButton(
                            text = stringResource(exit),
                            modifier = Modifier.align(Alignment.CenterHorizontally).width(200.dp),
                            onClick = {
                                navigator?.popUntilRoot()
                            }
                        )

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

        RegisterBackHandler(this) {
            navigator?.popUntilRoot()
        }
    }
}

