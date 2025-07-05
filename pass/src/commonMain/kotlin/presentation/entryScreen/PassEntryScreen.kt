package presentation.entryScreen

import org.example.hit.heal.core.presentation.components.VerticalTabletBaseScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.core.presentation.Resources.String.firstInstructionsPass
import org.example.hit.heal.core.presentation.Resources.String.firstMissionPass
import org.example.hit.heal.core.presentation.Resources.String.fmpt
import org.example.hit.heal.core.presentation.Resources.String.fmptMeaning
import org.example.hit.heal.core.presentation.Resources.String.next
import org.example.hit.heal.core.presentation.Resources.String.thePassTest
import org.example.hit.heal.core.presentation.Resources.String.welcomePass
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


        ObserveLifecycle(
            onStop = {
                viewModel.stopAudio()
            },
            onStart = {
                viewModel.onPlayAudioRequested(audioString)
            }
        )


        VerticalTabletBaseScreen(
            title = stringResource(welcomePass),
            content = {
                AudioPlayingAnimation(isPlaying = isPlaying,)

                Column(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        stringResource(fmpt),
                        color = primaryColor,
                        fontSize = 40.sp,
                        fontWeight = Bold
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(
                        stringResource(fmptMeaning),
                        color = primaryColor,
                        fontSize = 20.sp,
                        fontWeight = Bold
                    )
                    Spacer(modifier = Modifier.padding(50.dp))

                    Box(
                        modifier = Modifier.align(Alignment.CenterHorizontally).border(
                            width = 2.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(10.dp)
                        )
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
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
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
                    ) {
                        Text(
                            stringResource(thePassTest),
                            fontSize = 15.sp,
                            fontWeight = Bold,
                        )
                    }
                    Box(modifier = Modifier.fillMaxSize().padding(10.dp)) {
                        Button(
                            onClick = { navigator?.replace(AppDeviceScreen()) },
                            modifier = Modifier.align(Alignment.BottomCenter).width(200.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                            shape = RoundedCornerShape(12.dp),
                        ) {
                            Text(
                                text = stringResource(next),
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = Bold
                            )
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

