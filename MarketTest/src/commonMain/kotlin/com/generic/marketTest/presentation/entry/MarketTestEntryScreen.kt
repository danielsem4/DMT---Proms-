package com.generic.marketTest.presentation.entry

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.core.presentation.backgroundColor
import org.example.hit.heal.core.presentation.TextPrimary
import org.example.hit.heal.core.presentation.FontSize
import org.example.hit.heal.core.presentation.Sizes
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

/**
 * Entry screen for Market test
 * explanation about the test they are about to face
 */

class MarketTestEntryScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        BaseScreen(
            title = "Market Test",
            config = ScreenConfig.TabletConfig,
            topRightText = "0/10",
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = Sizes.paddingLg)
                        .padding(top = Sizes.paddingLg, bottom = Sizes.paddingLg)
                ) {
                    Card(
                        shape = CardDefaults.shape,
                        elevation = CardDefaults.cardElevation(Sizes.elevationMd),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .padding(Sizes.spacingMd)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(Sizes.paddingLg)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Please read the instructions below before starting the test.",
                                color = Color.Red,
                                fontSize = FontSize.EXTRA_MEDIUM,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = Sizes.spacingMd)
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = Sizes.spacingLg),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.spacedBy(Sizes.spacingMd)
                            ) {
                                Text("You will be presented with a series of market-related questions.", fontSize = FontSize.EXTRA_REGULAR, color = TextPrimary)
                                Text("Read each question carefully and answer to the best of your ability.", fontSize = FontSize.EXTRA_REGULAR, color = TextPrimary)
                                Text("There is no time limit, but try to answer promptly.", fontSize = FontSize.EXTRA_REGULAR, color = TextPrimary)
                                Text("Your progress will be shown at the top right.", fontSize = FontSize.EXTRA_REGULAR, color = TextPrimary)
                            }
                            Text("Good luck!", fontSize = FontSize.EXTRA_MEDIUM, color = primaryColor)
                        }
                    }
                    RoundedButton(
                        text = "Start Test",
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(bottom = Sizes.paddingLg),
                        onClick = {
                            // navigator?.replace(MarketTestScreen())
                        }
                    )
                }
            }
        )
    }
}