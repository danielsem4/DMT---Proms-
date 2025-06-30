package org.example.hit.heal.cdt.presentation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
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
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private enum class ScreenState { Initial, Animating, ShowContent }
private enum class ButtonState { Hidden, Visible }
private enum class TextState { Hidden, Visible }

class CDTLandingScreen : Screen {
    @Composable
    override fun Content() = CDTLandingScreenContent()
}

@Composable
fun CDTLandingScreenContent() {
    var state by remember { mutableStateOf(ScreenState.Initial) }
    var buttonState by remember { mutableStateOf(ButtonState.Hidden) }
    var textState by remember { mutableStateOf(TextState.Hidden) }
    val navigator = LocalNavigator.currentOrThrow

    // ── Auto-start animation after initial display ──────────────────────
    LaunchedEffect(Unit) {
        delay(800) // Slightly increased initial delay for a calmer start
        state = ScreenState.Animating
        println("LandingScreen: Auto-starting animation")
    }

    // ── State change handler ─────────────────────────────────────────────
    LaunchedEffect(state) {
        println("LandingScreen: state -> $state")
        if (state == ScreenState.Animating) {
            delay(1600) // Increased delay to ensure logo animation has fully settled
            state = ScreenState.ShowContent
            println("LandingScreen: state -> ShowContent (after delay)")
        } else if (state == ScreenState.ShowContent) {
            // Trigger button animation first
            buttonState = ButtonState.Visible
            // Wait for a short moment after the button animation starts before starting text animation
            delay(300) // Increased delay for better separation and smoother flow
            // Then trigger text animation
            textState = TextState.Visible
        }
    }


    TabletBaseScreen(
        title = stringResource(Res.string.clock_test_title),
        content = {
            if (state != ScreenState.ShowContent) {
                // Initial animation sequence (without BaseScreen)
                InitialStep(state)
            } else {
                // Final content using TabletBaseScreen
                SecondState(navigator, buttonState, textState)
            }
        }
    )

}

@Composable
private fun InitialStep(state: ScreenState) {
    BoxWithConstraints(Modifier.fillMaxSize()) {

        val finalLogoSize = 150.dp
        val finalLogoCenterX = 16.dp + (finalLogoSize / 2)
        val targetX = if (state == ScreenState.Initial) 0.dp else (finalLogoCenterX - maxWidth / 2)
        val targetY = if (state == ScreenState.Initial) 0.dp else (maxHeight / 2 - 16.dp - (finalLogoSize / 2))

        // Define a common animation spec for logo movements, correctly typed for Dp
        // Reduced stiffness further for slower movement, and adjusted dampingRatio for smoother settlement
        val logoAnimationSpec: AnimationSpec<Dp> = spring(dampingRatio = 0.7f, stiffness = 50f) // Softer spring

        val targetSize = if (state == ScreenState.Initial) 450.dp else finalLogoSize
        val animatedSize by animateDpAsState(
            targetValue = targetSize,
            animationSpec = logoAnimationSpec, // Reused spec
            finishedListener = {
                println("animateDpAsState size animation finished: $it")
            },
            label = "logo_size_animation"
        )

        val animatedOffsetX by animateDpAsState(
            targetValue = targetX,
            animationSpec = logoAnimationSpec, // Reused spec
            label = "logo_offset_x_animation"
        )

        val animatedOffsetY by animateDpAsState(
            targetValue = targetY,
            animationSpec = logoAnimationSpec, // Reused spec
            label = "logo_offset_y_animation"
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
private fun SecondState(navigator: Navigator, buttonState: ButtonState, textState: TextState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // "Clock test" text above the clock
        Text(
            text = stringResource(Res.string.clock_test_title),
            style = MaterialTheme.typography.h3,
            color = primaryColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Clock icon
        Image(
            painter = painterResource(Res.drawable.clock),
            contentDescription = stringResource(Res.string.clock_icon_description),
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Start button with slide-up animation
        val buttonOffsetY by animateDpAsState(
            targetValue = if (buttonState == ButtonState.Hidden) 100.dp else 0.dp,
            // Reduced stiffness and slightly higher damping for a more 'cushioned' and slower stop
            animationSpec = spring(dampingRatio = 0.75f, stiffness = 60f),
            label = "button_slide_animation"
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = buttonOffsetY),
            contentAlignment = Alignment.Center
        ) {
            RoundedButton(
                text = Res.string.start_button_text,
                onClick = {
                    navigator.replace(DrawClockScreen())
                },
                fontSize = 40.sp,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Description text with slide-in animation from bottom
        val textOffsetY by animateDpAsState(
            targetValue = if (textState == TextState.Hidden) 500.dp else 0.dp,
            // Increased duration further and ensured a good easing for smoothness
            animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
            label = "text_slide_animation"
        )
        val textAlpha by animateFloatAsState(
            targetValue = if (textState == TextState.Hidden) 0f else 1f,
            // Increased duration to match the longer slide, ensuring smooth fade-in over time
            animationSpec = tween(durationMillis = 1200),
            label = "text_fade_animation"
        )

        Text(
            text = stringResource(Res.string.footer_text),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .offset(y = textOffsetY)
                .fillMaxWidth()
                .alpha(textAlpha)
        )

        Spacer(modifier = Modifier.weight(1f))

        // Bottom section with logo and version
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            // HIT logo positioned at bottom left (final resting place for the animated logo)
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