package org.example.hit.heal.oriantation.feature.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.N
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import dmt_proms.oriantation.generated.resources.Res
import dmt_proms.oriantation.generated.resources.Res.string
import dmt_proms.oriantation.generated.resources.entry_Oriantation_instructions
import dmt_proms.oriantation.generated.resources.entry_Oriantation_title
import dmt_proms.oriantation.generated.resources.entry_Oriantation_welcome_note
import kotlinx.serialization.json.JsonNull.content
import org.jetbrains.compose.resources.stringResource
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.core.presentation.TabletBaseScreen
import org.example.hit.heal.oriantation.data.model.OrientationTestViewModel

class WelcomeScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = remember { OrientationTestViewModel() }
        TabletBaseScreen(
            title = (stringResource(string.entry_Oriantation_title)),
            question = 1,
            onNextClick = { navigator?.push(NumberSelectScreen(viewModel)) },
            content = {

                Spacer(modifier = Modifier.height(48.dp))

                // Main greeting + card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = (stringResource(string.entry_Oriantation_welcome_note)),
                        style = MaterialTheme.typography.h4.copy(fontSize = 32.sp),
                        color = Color(0xFF4EC3AF) // Use your primary color
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 120.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = 4.dp,
                        backgroundColor = Color.White,
                        border = BorderStroke(1.dp, Color(0xFF4EC3AF)) // Add green border
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = (stringResource(string.entry_Oriantation_instructions)),
                                color = Color(0xFF4EC3AF), // Green text
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.body1.copy(fontSize = 24.sp)
                            )
                        }
                    }
                }
                // No button here! The button is handled by TabletBaseScreen's onNextClick
            }
        )
    }
    }