package com.clock.test.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import dmt_proms.clock_test.generated.resources.Res
import dmt_proms.clock_test.generated.resources.clock
import dmt_proms.clock_test.generated.resources.clock_icon_description
import dmt_proms.clock_test.generated.resources.clock_test_title
import dmt_proms.clock_test.generated.resources.footer_text
import dmt_proms.clock_test.generated.resources.hit_logo
import dmt_proms.clock_test.generated.resources.hit_logo_description
import dmt_proms.clock_test.generated.resources.start_button_text
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

data class ClockTestScreen(
    private val onStartClick: () -> Unit
) : Screen {
    @Composable
    override fun Content() {
        TabletBaseScreen(
            title = stringResource(Res.string.clock_test_title),
            content = { innerComponents() }
        )
    }

    @Composable
    private fun innerComponents() {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))

                // Title
                Text(
                    text = stringResource(Res.string.clock_test_title),
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Normal,
                    color = primaryColor
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Clock Icon
                Image(
                    painter = painterResource(Res.drawable.clock),
                    contentDescription = stringResource(Res.string.clock_icon_description),
                    modifier = Modifier.size(160.dp)
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Start Button
                RoundedButton(
                    text = stringResource(Res.string.start_button_text),
                    modifier = Modifier
                        .width(280.dp)
                        .height(64.dp),
                    onClick = onStartClick,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Footer Text
                Text(
                    text = stringResource(Res.string.footer_text),
                    fontSize = 28.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 48.dp)
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            // Logo at bottom left
            Image(
                painter = painterResource(Res.drawable.hit_logo),
                contentDescription = stringResource(Res.string.hit_logo_description),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .size(80.dp)
            )
        }
    }
}