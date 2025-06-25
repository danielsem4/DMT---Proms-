package org.example.hit.heal.cdt.presentation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dmt_proms.clock_test.generated.resources.Res
import dmt_proms.clock_test.generated.resources.clock
import dmt_proms.clock_test.generated.resources.clock_icon_description
import dmt_proms.clock_test.generated.resources.clock_test_title
import dmt_proms.clock_test.generated.resources.footer_text
import dmt_proms.clock_test.generated.resources.hit_logo
import dmt_proms.clock_test.generated.resources.hit_logo_description
import dmt_proms.clock_test.generated.resources.start_button_text
import kotlinx.coroutines.delay
import org.example.hit.heal.core.presentation.backgroundColor
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private enum class ScreenState { Initial, Animating, ShowContent }

class CDTLandingScreen : Screen {
    @Composable
    override fun Content() = CDTLandingScreenContent()
}

@Composable
fun CDTLandingScreenContent() {
    var state by remember { mutableStateOf(ScreenState.Initial) }
    val navigator = LocalNavigator.currentOrThrow

    // ── Auto-start animation after initial display ──────────────────────
    LaunchedEffect(Unit) {
        delay(500) // Show initial state for 0.5 seconds (faster start)
        state = ScreenState.Animating
        println("LandingScreen: Auto-starting animation")
    }

    // ── State change handler ─────────────────────────────────────────────
    LaunchedEffect(state) {
        println("LandingScreen: state -> $state")
        if (state == ScreenState.Animating) {
            delay(800) // Wait for animation to complete
            state = ScreenState.ShowContent
            println("LandingScreen: state -> ShowContent (after delay)")
        }
    }


    TabletBaseScreen(
        title = stringResource(Res.string.clock_test_title),
        content = {
            if (state != ScreenState.ShowContent) {
                // Initial animation sequence (without BaseScreen)
                initialStep(state)
            } else {
                // Final content using TabletBaseScreen
                secondState(navigator)
            }
        }
    )

}

@Composable
private fun initialStep(state: ScreenState) {
    BoxWithConstraints(Modifier.fillMaxSize()) {

        val targetSize = if (state == ScreenState.Initial) 450.dp else 150.dp
        val animatedSize by animateDpAsState(
            targetValue = targetSize,
            animationSpec = tween(800),
            finishedListener = {
                println("animateDpAsState size animation finished: $it")
            }
        )

        val targetX = if (state == ScreenState.Initial) 0.dp else -(maxWidth / 2 - 100.dp)
        val animatedOffsetX by animateDpAsState(
            targetValue = targetX,
            animationSpec = tween(800)
        )

        val targetY = if (state == ScreenState.Initial) 0.dp else (maxHeight / 2 - 40.dp )
        val animatedOffsetY by animateDpAsState(
            targetValue = targetY,
            animationSpec = tween(800)
        )

        // ── Logo animation ───────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.hit_logo),
                contentDescription = stringResource(Res.string.hit_logo_description),
                modifier = Modifier
                    .size(animatedSize)
                    .offset(animatedOffsetX, animatedOffsetY)
            )
        }
    }
}

@Composable
private fun secondState(navigator: Navigator) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Clock icon
        Image(
            painter = painterResource(Res.drawable.clock),
            contentDescription = stringResource(Res.string.clock_icon_description),
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Description text
        Text(
            text = stringResource(Res.string.footer_text),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Start button
        RoundedButton(
            text = Res.string.start_button_text,
            onClick = {
                navigator.replace(DrawClockScreen())
            },
            fontSize = 40.sp,
        )


        Spacer(modifier = Modifier.weight(1f)) // Push bottom content down

        // Bottom section with logo and version
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            // HIT logo positioned at bottom left
            Image(
                painter = painterResource(Res.drawable.hit_logo),
                contentDescription = stringResource(Res.string.hit_logo_description),
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterStart)
            )

            // Version text positioned at bottom right
            Text(
                text = "version 3.0",
                style = MaterialTheme.typography.h5,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.BottomEnd),
            )
        }
    }
}