// FinalScreen.kt
package org.example.hit.heal.cdt.presentation

import ToastMessage
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.domain.DataError
import core.domain.Error
import dmt_proms.clock_test.generated.resources.Res
import dmt_proms.clock_test.generated.resources.exit_button_text
import dmt_proms.clock_test.generated.resources.final_screen_message
import dmt_proms.clock_test.generated.resources.final_screen_title
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.example.hit.heal.cdt.presentation.grade.ClockGrade
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Resources.String.clockGrade
import org.example.hit.heal.core.presentation.Resources.String.exit
import org.example.hit.heal.core.presentation.ToastType
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.InstructionBox
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class FinalScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val viewModel = koinViewModel<ClockTestViewModel>()
        val isSendingData by viewModel.isSendingData.collectAsState() // Observe the loading state
        val coroutineScope = rememberCoroutineScope()

        // Toast state
        var toastMessage by remember { mutableStateOf<String?>(null) }
        var toastType by remember { mutableStateOf(ToastType.Normal) }

        // Get the string resources in the composable context
        val successMessage = stringResource(Resources.String.sentSuccessfully)

        // upload the test data when this screen is first composed
        LaunchedEffect(Unit) {
            if (viewModel.circlePerfection.value != "" && viewModel.numbersSequence.value != "" && viewModel.handsPosition.value != "") {
                send(
                    viewModel,
                    coroutineScope,
                    onSuccess = {
                        toastMessage = successMessage
                        toastType = ToastType.Success
                        navigator.pop()
                    },
                    onFailure = { error ->
                        toastMessage = error.toString()
                        toastType = ToastType.Error
                        navigator.pop()
                    }
                )
            }
        }

        BaseScreen(
            config = ScreenConfig.TabletConfig,
            title = stringResource(Res.string.final_screen_title),
            content = {
                // ToastMessage shown conditionally
                toastMessage?.let { msg ->
                    ToastMessage(
                        message = msg,
                        type = toastType,
                        alignUp = false,
                        onDismiss = { toastMessage = null }
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    InstructionBox(
                        textResource = Res.string.final_screen_message,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
                    Spacer(modifier = Modifier.weight(0.5f))

                    if (isSendingData) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp),
                                color = primaryColor
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                text = stringResource(Resources.String.uploading) + "...",
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                color = primaryColor
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(0.5f))

                    Row {
                        RoundedButton(
                            text = stringResource(exit),
                            modifier = Modifier
                                .fillMaxWidth(0.3f)
                                .height(60.dp),
                            onClick = {
                                send(
                                    viewModel,
                                    coroutineScope,
                                    onSuccess = {
                                        navigator.pop()
                                        toastMessage = successMessage
                                        toastType = ToastType.Success
                                    },
                                    onFailure = { error ->
                                        navigator.pop()
                                        toastMessage = error.toString()
                                        toastType = ToastType.Error
                                    }
                                )
                            }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        RoundedButton(
                            text = clockGrade,
                            modifier = Modifier
                                .fillMaxWidth(0.3f)
                                .height(60.dp),
                            onClick = {
                                navigator.push(ClockGrade())
                            },
                        )
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        )
    }

    private fun send(
        viewModel: ClockTestViewModel,
        coroutineScope: CoroutineScope,
        onSuccess: () -> Unit,
        onFailure: (Error) -> Unit
    ) {
        try {
            coroutineScope.launch {
                viewModel.sendToServer(
                    onSuccess = {
                        onSuccess()
                    },
                    onFailure = { error ->
                        onFailure(error)
                    }
                )
            }
        } catch (error: Exception) {
            println(error.message)
            onFailure(DataError.Remote.UNKNOWN)
        }
    }
}
