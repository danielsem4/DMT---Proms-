package org.example.hit.heal.presentation.evaluation

import ContentWithMessageBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import org.example.hit.heal.core.presentation.Green
import org.example.hit.heal.core.presentation.Red
import org.example.hit.heal.core.presentation.Resources.String.evaluationText
import org.example.hit.heal.core.presentation.Resources.String.login
import org.example.hit.heal.core.presentation.Resources.String.next
import org.example.hit.heal.core.presentation.Resources.String.previous
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState

/**
 * EvaluationScreen is a screen that will be used to display evaluation-related content.
 * Questions, measurements, and other evaluation-related information will be displayed here.
 */

class EvaluationScreen: Screen {

    @Composable
    override fun Content() {

        val viewModel: EvaluationsViewModel = koinViewModel()
        val messageBarState = rememberMessageBarState()

        ContentWithMessageBar(
            messageBarState = messageBarState,
            position = MessageBarPosition.BOTTOM
        ) {
            BaseScreen(title = stringResource(evaluationText)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(evaluationText),
                            color = Green,
                            modifier = Modifier.padding(spacingMd)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(spacingMd),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        RoundedButton(
                            text = stringResource(previous),
                            onClick = {},
                            enabled = true
                        )
                        RoundedButton(
                            text = stringResource(next),
                            onClick = { },
                            enabled = true
                        )
                    }
                }
            }
        }

    }
}