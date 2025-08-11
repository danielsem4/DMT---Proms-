package org.example.hit.heal.oriantation.feature.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import org.example.hit.heal.core.presentation.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Resources.String.entryOrientationInstructions
import org.example.hit.heal.core.presentation.Resources.String.entryOrientationTitle
import org.example.hit.heal.core.presentation.Resources.String.entryOrientationWelcomeNote
import org.example.hit.heal.core.presentation.TabletBaseScreen
import org.example.hit.heal.oriantation.data.model.OrientationTestViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class OriantationWelcomeScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: OrientationTestViewModel = koinViewModel()
        TabletBaseScreen(
            title =stringResource(entryOrientationTitle) ,
            question = 1,
            onNextClick = { navigator?.push(NumberSelectScreen(viewModel)) },
            content = {


                // Main greeting + card
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = (stringResource(entryOrientationWelcomeNote)),
                        fontSize = LARGE,
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
                                text = (stringResource(entryOrientationInstructions)),
                                color = Color(0xFF4EC3AF), // Green text
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.body1.copy(fontSize = 24.sp)
                            )
                        }
                    }
                }
            }
        )
        RegisterBackHandler(this)
        {
            navigator?.popUntilRoot()
        }
    }
}