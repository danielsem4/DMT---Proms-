package presentation.entryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.ObserveLifecycle
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.FontSize.EXTRA_LARGE
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.FontSize.EXTRA_REGULAR
import org.example.hit.heal.core.presentation.Resources.String.firstInstructionsPass
import org.example.hit.heal.core.presentation.Resources.String.firstMissionPass
import org.example.hit.heal.core.presentation.Resources.String.fmpt
import org.example.hit.heal.core.presentation.Resources.String.fmptMeaning
import org.example.hit.heal.core.presentation.Resources.String.next
import org.example.hit.heal.core.presentation.Resources.String.thePassTest
import org.example.hit.heal.core.presentation.Resources.String.welcomePass
import org.example.hit.heal.core.presentation.Sizes.paddingLg
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.Sizes.paddingXl
import org.example.hit.heal.core.presentation.Sizes.paddingXs
import org.example.hit.heal.core.presentation.Sizes.radiusMd
import org.example.hit.heal.core.presentation.Sizes.spacingLg
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.appsDeviceScreen.AppDeviceScreen
import org.example.hit.heal.core.presentation.utils.animations.AudioPlayingAnimation

class PassEntryScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val viewModel: EntryViewModel = koinViewModel()
        val isOverlayVisible by viewModel.isOverlayVisible.collectAsState()
        val audioString = stringResource(firstInstructionsPass)
        val isPlaying by viewModel.isPlaying.collectAsState()

        BaseScreen(
            title = stringResource(welcomePass),
            config = ScreenConfig.TabletConfig,
            content = {

                // Animated visualization when audio is playing
                AudioPlayingAnimation(isPlaying = isPlaying)

                Spacer(modifier = Modifier.height(paddingLg))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = paddingSm),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(spacingLg),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            stringResource(fmpt),
                            color = primaryColor,
                            fontSize = EXTRA_LARGE,
                            fontWeight = Bold
                        )
                        Spacer(modifier = Modifier.padding(paddingSm))

                        Text(
                            stringResource(fmptMeaning),
                            color = primaryColor,
                            fontSize = EXTRA_MEDIUM,
                            fontWeight = Bold
                        )
                        Spacer(modifier = Modifier.padding(50.dp))

                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .border(
                                    width = 2.dp,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(radiusMd)
                                )
                                .background(Color.White, shape = RoundedCornerShape(radiusMd))
                                .padding(20.dp)
                        ) {
                            Text(
                                text = stringResource(firstMissionPass),
                                color = primaryColor,
                                fontSize = 35.sp,
                                fontWeight = Bold,
                                lineHeight = 60.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(paddingXs)
                        ) {
                            Text(
                                stringResource(thePassTest),
                                fontSize = EXTRA_REGULAR,
                                fontWeight = Bold,
                            )
                        }
                    }
                    RoundedButton(
                        text = stringResource(next),
                        modifier = Modifier.align(Alignment.CenterHorizontally).width(200.dp),
                        onClick = {
                            navigator?.replace(AppDeviceScreen())
                        }
                    )
                }
            }
        )

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

        RegisterBackHandler(this) {
            navigator?.pop()
        }
    }
}

