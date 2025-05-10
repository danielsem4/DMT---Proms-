package org.example.hit.heal.oriantation.feature.presentation

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
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.oriantation.generated.resources.Res
import dmt_proms.oriantation.generated.resources.entry_Oriantation_instructions
import dmt_proms.oriantation.generated.resources.entry_Oriantation_title
import dmt_proms.oriantation.generated.resources.entry_Oriantation_welcome_note
import kotlinx.serialization.json.JsonNull.content
import org.jetbrains.compose.resources.stringResource
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.core.presentation.TabletBaseScreen

class WelcomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        TabletBaseScreen(
            title = (stringResource(Res.string.entry_Oriantation_title  )),
//            onNextClick = { navigator.push() }
            buttonText = "אהב",
            question = 0,
            content = {

                Box(modifier = Modifier.fillMaxWidth()) {
//                    Text(
//                        text = "$question / 8",
//                        style = MaterialTheme.typography.body1.copy(fontSize = 16.sp),
//                        color = MaterialTheme.colors.primary,
//                        modifier = Modifier.align(Alignment.TopEnd)
//                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                // Main greeting + card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = (stringResource(Res.string.entry_Oriantation_welcome_note)),
                        style = MaterialTheme.typography.h4.copy(fontSize = 32.sp),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 120.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = 4.dp
                    ) {
                        Box(
                            modifier = Modifier
                                .background(Color.White)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = (stringResource(Res.string.entry_Oriantation_instructions)),
                                color =  Color.White,
                                textAlign = TextAlign.Center
                                )
                        }
                    }
                }
            }
        )
    }
}
