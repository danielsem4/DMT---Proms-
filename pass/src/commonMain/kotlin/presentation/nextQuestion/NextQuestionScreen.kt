package presentation.nextQuestion

import org.example.hit.heal.core.presentation.components.VerticalTabletBaseScreen
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.core.presentation.Resources.String.contact
import org.example.hit.heal.core.presentation.Resources.String.finishFirstMissionPass
import org.example.hit.heal.core.presentation.Resources.String.firstMissionDoneVocalPass
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.example.hit.heal.core.presentation.utils.animations.AudioPlayingAnimation
import presentation.dialScreen.DialScreen

class NextQuestionScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: NextQuestionViewModel = koinViewModel()
        val time by viewModel.time.collectAsState()
        val navigateToDialScreen by viewModel.navigateToDialScreen.collectAsState()
        val audioString = stringResource(firstMissionDoneVocalPass)
        val isPlaying by viewModel.isPlaying.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.onPlayAudioRequested(audioString)
        }

        LaunchedEffect(navigateToDialScreen) {
            if (navigateToDialScreen) {
                navigator?.push(DialScreen())
            }
        }

        VerticalTabletBaseScreen(
            title = stringResource(contact),
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
                        text = stringResource(finishFirstMissionPass),
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
