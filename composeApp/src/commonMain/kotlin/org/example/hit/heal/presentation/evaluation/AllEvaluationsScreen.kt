package org.example.hit.heal.presentation.evaluation

import ContentWithMessageBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.data.model.evaluation.Evaluation
import org.example.hit.heal.core.presentation.Resources.String.login
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.presentation.components.evaluation.EvaluationItemCard
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState

/**
 * AllEvaluationsScreen is a scrollable list that displays all evaluations.
 *
 */

class AllEvaluationsScreen: Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val viewModel: EvaluationsViewModel = koinViewModel()
        val items by viewModel.items.collectAsState()
        val messageBarState = rememberMessageBarState()

        ContentWithMessageBar(
            messageBarState = messageBarState,
            position = MessageBarPosition.BOTTOM
        ) {
            BaseScreen(title = stringResource(login)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(spacingMd)
                ) {
                    items(items, key = { it.id }) { item: Evaluation ->
                        EvaluationItemCard(
                            item = item,
                            onClick = {
                                // navigate to a specific evaluation screen
                                navigator.push(EvaluationScreen())
                            }
                        )
                    }
                }
            }
        }
    }

}