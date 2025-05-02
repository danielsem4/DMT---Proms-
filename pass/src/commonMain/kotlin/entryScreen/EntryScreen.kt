package entryScreen

import BaseTabletScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.speaker
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class EntryScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        var isDialogVisible by remember { mutableStateOf(false) }
        var isAudioClicked by remember { mutableStateOf(false) }
      //  val audioPlayer = remember { AudioPlayer() }
       // val audioUrl = audioResourceId?.let { stringResource(it) }

//        LaunchedEffect(isAudioClicked) {
//            if (isAudioClicked) {
//                audioUrl?.let {
//                    isDialogVisible = true
//                    audioPlayer.play(it) {
//                        isDialogVisible = false
//                    }
//                    isAudioClicked = false
//                }
//            }
//        }

        BaseTabletScreen(
            title = "אפליקציות המכשיר",
            content = {
                Button(
                    onClick = {
                      //  sixthQuestionViewModel.setRandomAudio()
                        isAudioClicked = true
                    },
                    colors = ButtonDefaults.buttonColors(primaryColor),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(30)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(Res.drawable.speaker),
                            contentDescription = "Audio",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(30.dp)
                                .background(Color.Transparent)
                        )
                        Text(
                            text = "הקשב",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        )
    }
}
