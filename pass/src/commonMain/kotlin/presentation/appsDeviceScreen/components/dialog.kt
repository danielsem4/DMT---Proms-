package presentation.appsDeviceScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.close
import dmt_proms.pass.generated.resources.exclamation_mark
import dmt_proms.pass.generated.resources.like
import kotlinx.coroutines.delay
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.jetbrains.compose.resources.painterResource
import presentation.components.AudioPlayingAnimation

@Composable
fun InstructionsDialog(
    text: String,
    secondsLeft: Int,
    isPlaying: Boolean,
    isCountdownActive: Boolean,
    shouldShowCloseIcon: Boolean,
    onPlayAudio: () -> Unit,
    onDismiss: () -> Unit
) {

    val canDismiss = isCountdownActive && secondsLeft == 0

    LaunchedEffect(isCountdownActive, secondsLeft) {
        if (isCountdownActive && secondsLeft == 0) {
            onDismiss()
        }
    }

    LaunchedEffect(Unit) {
        onPlayAudio()
    }

    Dialog(onDismissRequest = {
        if (canDismiss) {
            onDismiss()
        }
    }) {
        Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {

            Image(
                painter = painterResource(Res.drawable.exclamation_mark),
                contentDescription = "profile icon",
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.TopCenter)
                    .zIndex(1f)
            )

            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = 8.dp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth().height(40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (isCountdownActive && shouldShowCloseIcon) {
                            Image(
                                painter = painterResource(Res.drawable.close),
                                contentDescription = "Close",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        onDismiss()
                                    }
                            )
                        }
                        if (isCountdownActive) {
                            Text(
                                text = "$secondsLeft",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = primaryColor
                            )
                        }

                        AudioPlayingAnimation(
                            isPlaying = isPlaying,
                            size1 = 20f,
                            size2 = 40f,
                            size3 = 60f,
                            imageSize = 30.dp,
                            strokeWidth = 2f
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = text,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun CheckUnderstandingDialog(
    onYesClick: () -> Unit,
    onNoClick: () -> Unit,
    timeoutMillis: Long = 15_000L
) {
    LaunchedEffect(Unit) {
        delay(timeoutMillis)
        onYesClick()
    }

    Dialog(onDismissRequest = { onYesClick() }) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(Res.drawable.like),
                contentDescription = "like",
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.TopCenter)
                    .zIndex(1f)
            )

            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = 8.dp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(30.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "האם ההוראה הייתה מובנת?",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor
                    )

                    Spacer(modifier = Modifier.padding(25.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = onYesClick,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                        ) {
                            Text(
                                "כן",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = primaryColor
                            )
                        }

                        Button(
                            onClick = onNoClick,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                        ) {
                            Text(
                                "לא",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = primaryColor
                            )
                        }
                    }
                }
            }
        }
    }
}



