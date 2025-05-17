package presentation.nextQuestion

import BaseTabletScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.contact
import dmt_proms.pass.generated.resources.finish_first_mission_pass
import dmt_proms.pass.generated.resources.first_instructions_pass
import dmt_proms.pass.generated.resources.first_mission_done_vocal_pass
import dmt_proms.pass.generated.resources.search
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.components.AudioPlayingAnimation
import presentation.dialScreen.DialScreen

class NextQuestionScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: NextQuestionViewModel = koinViewModel()
        val time by viewModel.time.collectAsState()
        val navigateToDialScreen by viewModel.navigateToDialScreen.collectAsState()
        val audioString = stringResource(Res.string.first_mission_done_vocal_pass)
        val isPlaying by viewModel.isPlaying.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.playAudio(audioString)
        }

        LaunchedEffect(navigateToDialScreen) {
            if (navigateToDialScreen) {
                navigator?.push(DialScreen())
            }
        }

        BaseTabletScreen(
            title = stringResource(Res.string.contact),
            content = {
                AudioPlayingAnimation(isPlaying = isPlaying,)
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(10.dp))
                        .border(
                            width = 2.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.finish_first_mission_pass),
                        color = primaryColor,
                        fontSize = 30.sp,
                        fontWeight = Bold,
                        lineHeight = 40.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.padding(10.dp))

                    Text(
                        text = time.toString(),
                        color = primaryColor,
                        fontSize = 30.sp,
                        fontWeight = Bold,
                    )

                }
            }}
        )
    }
}
