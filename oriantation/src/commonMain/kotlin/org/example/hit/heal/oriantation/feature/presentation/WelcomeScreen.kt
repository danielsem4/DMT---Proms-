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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.FontSize.EXTRA_LARGE
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.Resources.String.entryOrientationInstructions
import org.example.hit.heal.core.presentation.Resources.String.entryOrientationTitle
import org.example.hit.heal.core.presentation.Resources.String.entryOrientationWelcomeNote
import org.example.hit.heal.core.presentation.Sizes.elevationMd
import org.example.hit.heal.core.presentation.Sizes.paddingLg
import org.example.hit.heal.core.presentation.Sizes.radiusLg
import org.example.hit.heal.core.presentation.Sizes.radiusMd2
import org.example.hit.heal.core.presentation.TabletBaseScreen
import org.example.hit.heal.core.presentation.backgroundColor
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.oriantation.data.model.OrientationTestViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * Welcome screen for the orientation test
 */

class OriantationWelcomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: OrientationTestViewModel = koinViewModel()

        TabletBaseScreen(
            title = stringResource(entryOrientationTitle),
            question = 1,
            onNextClick = { navigator?.push(NumberSelectScreen(viewModel)) },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = paddingLg),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(entryOrientationWelcomeNote),
                        fontSize = EXTRA_LARGE,
                        style = MaterialTheme.typography.h4,
                        color = primaryColor
                    )
                    Spacer(modifier = Modifier.height(paddingLg))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 120.dp),
                        shape = RoundedCornerShape(radiusMd2),
                        elevation = elevationMd,
                        backgroundColor = backgroundColor,
                        border = BorderStroke(1.dp, primaryColor)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(radiusLg)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(entryOrientationInstructions),
                                color = primaryColor,
                                textAlign = TextAlign.Center,
                                fontSize = EXTRA_MEDIUM,
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
            }
        )

        RegisterBackHandler(this) {
            navigator?.popUntilRoot()
        }
    }
}
