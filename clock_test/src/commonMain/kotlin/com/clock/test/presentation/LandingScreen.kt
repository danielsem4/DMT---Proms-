package com.clock.test.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import dmt_proms.clock_test.generated.resources.Res
import dmt_proms.clock_test.generated.resources.hit_logo
import org.jetbrains.compose.resources.painterResource

class LandingScreen :Screen{
    @Composable
    override fun Content() {

        TabletBaseScreen(title = "Landing Screen", content = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.hit_logo),
                    contentDescription = "HIT Logo",
                    modifier = Modifier.size(450.dp)
                )
            }
        })
    }
}