package org.example.hit.heal.hitber.utils

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedNavigator(content: @Composable () -> Unit) {
    val navigator = LocalNavigator.current
    val currentScreen = navigator?.lastItem

    if (navigator == null) return

    AnimatedContent(
        targetState = currentScreen,
        transitionSpec = {
            slideInVertically(initialOffsetY = { it }) + fadeIn() with
                    slideOutVertically(targetOffsetY = { -it }) + fadeOut()
        },
        contentKey = { it?.key }
    ) {
        content()
    }
}
