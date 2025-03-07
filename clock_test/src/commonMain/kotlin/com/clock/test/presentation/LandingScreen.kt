package com.clock.test.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dmt_proms.clock_test.generated.resources.Res
import dmt_proms.clock_test.generated.resources.clock_test
import dmt_proms.clock_test.generated.resources.hit_logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun LandingScreen() {
    TabletBaseScreen(stringResource(Res.string.clock_test), content = {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.hit_logo),
                contentDescription = "Hit Logo",
                modifier = Modifier.size(450.dp)
            )
        }
    })
}