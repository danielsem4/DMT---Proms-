package com.clock.test.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import dmt_proms.clock_test.generated.resources.Res
import dmt_proms.clock_test.generated.resources.completion_screen_message
import dmt_proms.clock_test.generated.resources.completion_screen_message_next
import dmt_proms.clock_test.generated.resources.completion_screen_title
import dmt_proms.clock_test.generated.resources.next_button_text
import org.example.hit.heal.core.presentation.Colors
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.jetbrains.compose.resources.stringResource

data class CompletionScreen(
    private val onNextClick: () -> Unit
) : Screen {
    @Composable
    override fun Content() {
        var isFirstMessage by remember { mutableStateOf(true) }
        
        TabletBaseScreen(
            title = stringResource(Res.string.completion_screen_title),
            topRightText = "1/3",
            content = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(
                                if (isFirstMessage) Res.string.completion_screen_message
                                else Res.string.completion_screen_message_next
                            ),
                            fontSize = 36.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Colors.primaryColor,
                            textAlign = TextAlign.Center,
                            lineHeight = 45.sp,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                    
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
                                onNextClick()
                            }
                        }
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        )
    }
} 