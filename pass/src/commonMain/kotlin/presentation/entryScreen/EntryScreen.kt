package presentation.entryScreen

import BaseTabletScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.speaker
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import presentation.appsDeviceScreen.AppDeviceScreen

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
            title = "ברוך הבא!",
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
                        Text(
                            text = "הקשב/י",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = Bold
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Image(
                            painter = painterResource(Res.drawable.speaker),
                            contentDescription = "Audio",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(30.dp)
                                .background(Color.Transparent)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("FMPT", color = primaryColor, fontSize = 30.sp, fontWeight = Bold)
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text("Functional Mobile Phone Test", color = primaryColor, fontSize = 20.sp, fontWeight = Bold)

                    Box(
                        modifier = Modifier.align(Alignment.CenterHorizontally).border(width = 2.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp))
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "שלום, המרפאה בעיסוק רוצה לבדוק את היכולת התפקודית בפלאפון. בדקות הקרובות יופיע בטאבלט, שמשתמש כמכשיר הפלאפון, שתי משימות הקשורות לשימוש בפלאפון. פעלו לפי ההוראות. מתחילים.",
                            color = primaryColor,
                            fontSize = 30.sp,
                            fontWeight = Bold,
                            lineHeight = 40.sp
                        )
                    }

                    Box(
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
                    ) {
                        Text(
                            "מבוסס על מבחן ה-PASS.",
                            fontSize = 15.sp,
                            fontWeight = Bold,
                        )
                    }
                    Box(modifier = Modifier.fillMaxSize().padding(10.dp)){
                    Button(
                        onClick = {navigator?.push(AppDeviceScreen())},
                        modifier = Modifier.align(Alignment.BottomCenter).width(200.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Text(
                            text = "הבא",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = Bold
                        )
                    }}



                }
            }
        )
    }
}