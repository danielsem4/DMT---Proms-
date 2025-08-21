package org.example.hit.heal.presentation.evaluation


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Resources.String.evaluationText
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.ToastType
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.presentation.components.evaluation.EvaluationItemCard
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * AllEvaluationsScreen is a scrollable list that displays all evaluations.
 *
 */

class AllEvaluationsScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val vm: EvaluationsViewModel = koinViewModel()
        val evaluations by vm.evalItems.collectAsState()
        val isLoading by vm.isLoading.collectAsState()
        val error by vm.errorMessage.collectAsState()

        LaunchedEffect(Unit) {
            vm.getAllEvaluations()
        }

        BaseScreen(
            title = stringResource(evaluationText),
            onPrevClick = {
                navigator.pop()
            }) {
            Box(Modifier.fillMaxSize()) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                if (error != null && !error.isNullOrEmpty()) {
                    LaunchedEffect(error) {

                    }
                }

                if (!isLoading && evaluations.isEmpty()) {
                    Text(
                        text = error ?: "No evaluations found",
                        fontSize = LARGE,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                if (!isLoading && evaluations.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(spacingMd)
                    ) {
                        items(evaluations, key = { it.id }) { evaluation ->
                            EvaluationItemCard(
                                item = evaluation,
                                onClick = {
                                    navigator.push(EvaluationTestScreen(evaluation))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}