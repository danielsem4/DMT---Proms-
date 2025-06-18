package presentation.endScreen

import BaseTabletScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import org.example.hit.heal.core.presentation.Colors.primaryColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.util.lerp
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.check
import dmt_proms.pass.generated.resources.end
import dmt_proms.pass.generated.resources.exit
import dmt_proms.pass.generated.resources.thanks_coffe
import dmt_proms.pass.generated.resources.thanks_vocal_pass

import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.example.hit.heal.core.presentation.components.AudioPlayingAnimation
import org.example.hit.heal.core.presentation.components.SuccessAnimation
import presentation.entryScreen.EntryViewModel
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class EndScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        val viewModel: EntryViewModel = koinViewModel()
        val isOverlayVisible by viewModel.isOverlayVisible.collectAsState()
        val audioString = stringResource(Res.string.thanks_vocal_pass)
        val isPlaying by viewModel.isPlaying.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.onPlayAudioRequested(audioString)
        }

        BaseTabletScreen(
            title = stringResource(Res.string.end),
            content = {
                AudioPlayingAnimation(isPlaying = isPlaying)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                            .border(
                                width = 2.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(top = 16.dp, bottom = 16.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.thanks_coffe),
                            color = primaryColor,
                            fontSize = 35.sp,
                            fontWeight = Bold,
                            lineHeight = 40.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    SuccessAnimation(modifier = Modifier.size(100.dp))

                    Box(modifier = Modifier.fillMaxSize().padding(10.dp)) {
                        Button(
                            onClick = {  },
                            modifier = Modifier.align(Alignment.BottomCenter).width(200.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                            shape = RoundedCornerShape(12.dp),
                        ) {
                            Text(
                                text = stringResource(Res.string.exit),
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

