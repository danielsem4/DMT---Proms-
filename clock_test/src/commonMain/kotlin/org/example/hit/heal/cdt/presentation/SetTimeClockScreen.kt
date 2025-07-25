package org.example.hit.heal.cdt.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import dmt_proms.clock_test.generated.resources.Res
import dmt_proms.clock_test.generated.resources.clock_instruction
import dmt_proms.clock_test.generated.resources.clock_instruction_twelve
import dmt_proms.clock_test.generated.resources.clock_screen_title
import dmt_proms.clock_test.generated.resources.next_button_text
import org.example.hit.heal.cdt.data.ClockTime
import org.example.hit.heal.cdt.presentation.components.ClockComponent
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.InstructionBox
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class SetTimeClockScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<ClockTestViewModel>()
        var isButtonEnabled by  remember { mutableStateOf(true) }

        // Start timing for setting the first clock when the screen loads
        viewModel.startSettingFirstClockTimer()

        // Reset the time to 12:0 if this is the first step
        val isSecondStep by viewModel.getSecondStep().collectAsState()
        if (!isSecondStep) {
            viewModel.updateFirstTime(ClockTime(12, 0))
        }

        val currentTime by viewModel.getCurrentClockSetTime().collectAsState()

        BaseScreen(
            config = ScreenConfig.TabletConfig,
            title = stringResource(Res.string.clock_screen_title),
            topRightText = if (isSecondStep) "3/3" else "2/3",
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ClockComponent(
                            modifier = Modifier.weight(0.4f),
                            initialTime = currentTime,
                            onTimeChange = { newTime ->
                                if (!isSecondStep) {
                                    viewModel.updateFirstTime(newTime)
                                } else {
                                    viewModel.updateSecondTime(newTime)
                                }
                            }
                        )

                        Spacer(modifier = Modifier.width(32.dp))

                        InstructionBox(
                            textResource = if (isSecondStep)
                                Res.string.clock_instruction_twelve
                            else Res.string.clock_instruction,
                            modifier = Modifier.weight(0.6f)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    RoundedButton(
                        text = stringResource(Res.string.next_button_text),
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(60.dp),
                        onClick = {
                            if (!isButtonEnabled) return@RoundedButton

                            if (!isSecondStep) {
                                viewModel.setSecondStep(true)
                                // Stop timing for setting the first clock
                                viewModel.stopSettingFirstClockTimer()
                                // Start timing for setting the second clock
                                viewModel.startSettingSecondClockTimer()
                            } else {
                                isButtonEnabled = false
                                // Stop timing for setting the second clock
                                viewModel.stopSettingSecondClockTimer()
                                navigator.replace(FinalScreen())
                            }
                        },
                        enabled = isButtonEnabled
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        )
    }
}