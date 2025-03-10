package com.clock.test.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.clock.test.presentation.components.InstructionBox
import com.clock.test.utils.exitApplication
import dmt_proms.clock_test.generated.resources.Res
import dmt_proms.clock_test.generated.resources.exit_button_text
import dmt_proms.clock_test.generated.resources.final_screen_message
import dmt_proms.clock_test.generated.resources.final_screen_title
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.jetbrains.compose.resources.stringResource

class FinalScreen : Screen {
    @Composable
    override fun Content() {
        TabletBaseScreen(
            title = stringResource(Res.string.final_screen_title),
            topRightText = "3/3",
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    
                    InstructionBox(
                        textResource = Res.string.final_screen_message
                    )
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    RoundedButton(
                        text = stringResource(Res.string.exit_button_text),
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(60.dp),
                        onClick = {
                            exitApplication()
                        }
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        )
    }
} 