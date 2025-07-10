package org.example.hit.heal.presentation.evaluation

import ContentWithMessageBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.data.model.evaluation.Evaluation
import org.example.hit.heal.core.presentation.Resources.String.evaluationText
import org.example.hit.heal.core.presentation.Resources.String.login
import org.example.hit.heal.core.presentation.Resources.String.serverError
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
        val vm: EvaluationsViewModel = koinViewModel()
        val items by vm.items.collectAsState()
        val isLoading by vm.isLoading.collectAsState()
        val error by vm.errorMessage.collectAsState()
        val messageBarState = rememberMessageBarState()

        LaunchedEffect(Unit) {
            vm.getAllEvaluations()
        }

        ContentWithMessageBar(
            messageBarState = messageBarState,
            position = MessageBarPosition.BOTTOM
        ) {
            BaseScreen(title = stringResource(evaluationText)) {
                Box(Modifier.fillMaxSize()) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    error?.let { msg ->
                        LaunchedEffect(msg) {
                            messageBarState.addError(msg)
                        }
                    }
                    if (!isLoading && items.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(spacingMd)
                        ) {
                            items(items, key = { it.id }) { item ->
                                EvaluationItemCard(
                                    item = item,
                                    onClick = {
                                        navigator.push(EvaluationScreen())
                                    }
                                )
                            }
                        }
                    }
                    if (!isLoading && items.isEmpty() && error == null) {
                        Text(
                            text = stringResource(serverError),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}