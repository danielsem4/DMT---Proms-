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
import androidx.compose.material.MaterialTheme
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
import com.clock.test.domain.VersionInfo
import dmt_proms.clock_test.generated.resources.Res
import dmt_proms.clock_test.generated.resources.clock
import dmt_proms.clock_test.generated.resources.clock_test_title
import dmt_proms.clock_test.generated.resources.footer_text
import dmt_proms.clock_test.generated.resources.hit_logo
import dmt_proms.clock_test.generated.resources.start_button_text
import dmt_proms.clock_test.generated.resources.version
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = stringResource(Res.string.clock_test_title),
                    fontSize = MaterialTheme.typography.h1.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Clock Icon (Local Image)
                Image(
                    painter = painterResource(Res.drawable.clock), // Ensure this resource exists
                    contentDescription = "Clock Icon",
                    modifier = Modifier.size(200.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Start Button
                RoundedButton(
                    Res.string.start_button_text,
                    modifier = Modifier
                        .width(180.dp)
                        .height(60.dp),
                    onStartClick
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Footer Text
                Text(
                    text = stringResource(Res.string.footer_text),
                    fontSize = 32.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            Image(
                painter = painterResource(Res.drawable.hit_logo), "hit",
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .size(200.dp)
            )

            Text(
                text = stringResource(Res.string.version) + " " + VersionInfo.VERSION_NAME,
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp),
                color = Color.Gray
            )
        }
    }
}