package presentation.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Resources.Icon.closeIcon
import org.example.hit.heal.core.presentation.Resources.Icon.exclamationMark
import org.example.hit.heal.core.presentation.Resources.String.dialogClose
import org.example.hit.heal.core.presentation.Resources.String.dialogExclamationMark
import org.example.hit.heal.core.presentation.Sizes.elevationLg
import org.example.hit.heal.core.presentation.Sizes.iconSizeLg
import org.example.hit.heal.core.presentation.Sizes.iconSizeMd
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.Sizes.paddingXl
import org.example.hit.heal.core.presentation.Sizes.radiusLg
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.core.presentation.utils.animations.AudioPlayingAnimation
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

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
        Box(modifier = Modifier.fillMaxWidth().height(320.dp)) {

            Image(
                painter = painterResource(exclamationMark),
                contentDescription = stringResource(dialogExclamationMark),
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.TopCenter)
                    .zIndex(1f)
            )

            Card(
                shape = RoundedCornerShape(radiusLg),
                elevation = elevationLg,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = paddingXl)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = paddingMd, vertical = paddingMd)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.weight(1f)
                        ) {
                            if (isCountdownActive && shouldShowCloseIcon) {
                            Image(
                                painter = painterResource(closeIcon),
                                contentDescription = stringResource(dialogClose),
                                modifier = Modifier
                                    .size(iconSizeMd)
                                    .clickable { onDismiss() }
                            )
                        }

                            Spacer(modifier = Modifier.width(paddingSm))

                            if (isCountdownActive) {
                                Text(
                                    text = "$secondsLeft",
                                    fontSize = EXTRA_MEDIUM,
                                    fontWeight = FontWeight.Bold,
                                    color = primaryColor
                                )
                            }

                        }

                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (isPlaying) {
                                AudioPlayingAnimation(
                                    isPlaying = isPlaying,
                                    size1 = 20f,
                                    size2 = 40f,
                                    size3 = 60f,
                                    imageSize = iconSizeLg,
                                    strokeWidth = 2f
                                )
                            }
                        }

                    }

                    Spacer(modifier = Modifier.height(paddingXl))

                    Text(
                        text = text,
                        fontSize = LARGE,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        lineHeight = 50.sp
                    )
                }
            }
        }
    }
}

//@Composable
//fun CheckUnderstandingDialog(
//    onYesClick: () -> Unit,
//    onNoClick: () -> Unit,
//
//) {
//    LaunchedEffect(Unit) {
//        delay(25_000)
//        onYesClick()
//    }
//
//    Dialog(onDismissRequest = { onYesClick() }) {
//        Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
//            Image(
//                painter = painterResource(likeIcon),
//                contentDescription = stringResource(dialogLike),
//                modifier = Modifier
//                    .size(60.dp)
//                    .align(Alignment.TopCenter)
//                    .zIndex(1f)
//            )
//
//            Card(
//                shape = RoundedCornerShape(radiusLg),
//                elevation = elevationLg,
//                modifier = Modifier
//                    .align(Alignment.Center)
//                    .padding(top = paddingXs)
//            ) {
//                Column(
//                    modifier = Modifier
//                        .padding(30.dp)
//                        .fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = stringResource(understandingDialogText),
//                        textAlign = TextAlign.Center,
//                        fontSize = 30.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = primaryColor
//                    )
//
//                    Spacer(modifier = Modifier.padding(35.dp))
//
//                    Row(
//                        horizontalArrangement = Arrangement.SpaceEvenly,
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Button(
//                            onClick = onYesClick,
//                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
//                        ) {
//                            Text(
//                                stringResource(yes),
//                                fontSize = 25.sp,
//                                fontWeight = FontWeight.Bold,
//                                color = primaryColor
//                            )
//                        }
//
//                        Button(
//                            onClick = onNoClick,
//                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
//                        ) {
//                            Text(
//                                stringResource(no),
//                                fontSize = 25.sp,
//                                fontWeight = FontWeight.Bold,
//                                color = primaryColor
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//

