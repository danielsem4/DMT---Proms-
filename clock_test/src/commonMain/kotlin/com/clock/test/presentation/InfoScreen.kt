package com.clock.test.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clock.test.presentation.components.ClockTime
import com.clock.test.presentation.components.InstructionBox
import dmt_proms.clock_test.generated.resources.Res
import dmt_proms.clock_test.generated.resources.completion_screen_message
import dmt_proms.clock_test.generated.resources.completion_screen_message_next
import dmt_proms.clock_test.generated.resources.completion_screen_title
import dmt_proms.clock_test.generated.resources.next_button_text
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

class InfoScreen : Screen {
    @Composable
    override fun Content() {
        var isFirstMessage by remember { mutableStateOf(true) }
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinInject<ClockTestViewModel>()

        // Reset the state of the clock step before use
        viewModel.setSecondStep(false)

        TabletBaseScreen(
            title = stringResource(Res.string.completion_screen_title),
            topRightText = "1/3",
            content = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    InstructionBox(
                        textResource = if (isFirstMessage)
                            Res.string.completion_screen_message
                        else Res.string.completion_screen_message_next
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    RoundedButton(
                        text = stringResource(Res.string.next_button_text),
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(60.dp),
                        onClick = {
                            if (isFirstMessage) {
                                isFirstMessage = false
                            } else {
                                // Reset the time to 12:0 before navigating to the clock screen
                                viewModel.updateFirstTime(ClockTime(12, 0))
                                navigator.replace(SetTimeClockScreen())
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        )
    }
}