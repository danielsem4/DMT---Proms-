package presentation.entryScreen

import BaseTabletScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.first_instructions_pass
import dmt_proms.pass.generated.resources.first_mission_pass
import dmt_proms.pass.generated.resources.fmpt
import dmt_proms.pass.generated.resources.fmpt_meaning
import dmt_proms.pass.generated.resources.next
import dmt_proms.pass.generated.resources.speaker
import dmt_proms.pass.generated.resources.test_record_pass
import dmt_proms.pass.generated.resources.the_pass_test
import dmt_proms.pass.generated.resources.welcome
import dmt_proms.pass.generated.resources.what_do_you_need_to_do_pass
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.appsDeviceScreen.AppDeviceScreen
import presentation.components.AudioPlayer
import presentation.components.AudioPlayingAnimation

class EntryScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val viewModel: EntryViewModel = koinViewModel()
        val isOverlayVisible by viewModel.isOverlayVisible.collectAsState()
        val audioString = stringResource(Res.string.first_instructions_pass)
        val isPlaying by viewModel.isPlaying.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.onPlayAudioRequested(audioString)
        }

        BaseTabletScreen(
            title = stringResource(Res.string.welcome),
            content = {
                AudioPlayingAnimation(isPlaying = isPlaying,)

                Column(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        stringResource(Res.string.fmpt),
                        color = primaryColor,
                        fontSize = 40.sp,
                        fontWeight = Bold
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(
                        stringResource(Res.string.fmpt_meaning),
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
                            text = stringResource(Res.string.first_mission_pass),
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
                            stringResource(Res.string.the_pass_test),
                            fontSize = 15.sp,
                            fontWeight = Bold,
                        )
                    }
                    Box(modifier = Modifier.fillMaxSize().padding(10.dp)) {
                        Button(
                            onClick = { navigator?.push(AppDeviceScreen()) },
                            modifier = Modifier.align(Alignment.BottomCenter).width(200.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                            shape = RoundedCornerShape(12.dp),
                        ) {
                            Text(
                                text = stringResource(Res.string.next),
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

