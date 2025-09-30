package org.example.hit.heal.hitber.presentation.shapes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.RegisterBackHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.hit.heal.core.presentation.Resources.Icon.profileIcon
import org.example.hit.heal.core.presentation.Resources.String.`continue`
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberDialogInstructions
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberTask
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberTitle
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberUnderstand
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.components.dialogs.CustomDialog
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * Show the 5 shapes for 30 seconds
 */
class ShapeScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val secondQuestionViewModel: SecondQuestionViewModel = koinViewModel()
        var showDialog by remember { mutableStateOf(true) }

        var secondsLeft by remember { mutableStateOf(0) }
        var timerJob by remember { mutableStateOf<Job?>(null) } // keep reference

        val shapeSet by secondQuestionViewModel.selectedSet.collectAsState()

        BaseScreen(
            title = stringResource(secondQuestionHitberTitle),
            config = ScreenConfig.TabletConfig,
            topRightText = "2/10",
            content = {
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .background(color = Color.White),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        shapeSet.forEach { shapeRes ->
                            Icon(
                                painter = painterResource(shapeRes.drawable),
                                contentDescription = stringResource(secondQuestionHitberTitle),
                                tint = Color.Unspecified,
                                modifier = Modifier.size(150.dp)
                            )
                        }
                    }

                    RoundedButton(
                        text = stringResource(`continue`),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 24.dp)
                            .width(200.dp),
                        onClick = {
                            // cancel timer if active
                            timerJob?.cancel()
                            timerJob = null
                            navigator?.replace(ActionShapesScreen(2))
                        }
                    )

                    if (secondsLeft > 0) {
                        Text(
                            text = "${secondsLeft}s",
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(12.dp),
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        )

        // Load shapes only if not already set
        LaunchedEffect(Unit) {
            secondQuestionViewModel.ensureShapeSet()
        }

        RegisterBackHandler(this) {
            timerJob?.cancel()
            secondQuestionViewModel.resetEverything()
            navigator?.pop()
        }

        // Show dialog
        if (showDialog) {
            CustomDialog(
                icon = profileIcon,
                title = stringResource(secondQuestionHitberTask),
                description = stringResource(secondQuestionHitberDialogInstructions),
                onDismiss = { showDialog = false },
                buttons = listOf(
                    stringResource(secondQuestionHitberUnderstand) to { showDialog = false },
                )
            )
        }

        // Start timer after dialog closes
        LaunchedEffect(showDialog) {
            if (!showDialog) {
                secondsLeft = 30
                timerJob?.cancel() // cancel previous if exists
                timerJob = launch {
                    while (secondsLeft > 0) {
                        delay(1000)
                        secondsLeft -= 1
                    }
                    navigator?.replace(ActionShapesScreen(2))
                }
            }
        }
    }
}
