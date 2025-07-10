// FinalScreen.kt
package org.example.hit.heal.cdt.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dmt_proms.clock_test.generated.resources.Res
import dmt_proms.clock_test.generated.resources.exit_button_text
import dmt_proms.clock_test.generated.resources.final_screen_message
import dmt_proms.clock_test.generated.resources.final_screen_title
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.example.hit.heal.core.presentation.Resources
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
        val snackBarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()

        // Get the string resources in the composable context
        val successMessage = stringResource(Resources.String.sentSuccessfully)

        BaseScreen(
            config = ScreenConfig.TabletConfig,
            title = stringResource(Res.string.final_screen_title),
            content = {
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

                    // Conditionally show "Uploading..." message
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
                                fontSize = MaterialTheme.typography.h4.fontSize,
                                color = primaryColor
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(0.5f))

                    Row {
                        RoundedButton(
                            text = stringResource(Res.string.exit_button_text),
                            modifier = Modifier
                                .fillMaxWidth(0.3f)
                                .height(60.dp),
                            onClick = {
                                navigator.pop()
                            }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        RoundedButton(
                            text = Resources.String.send,
                            modifier = Modifier
                                .fillMaxWidth(0.3f)
                                .height(60.dp),
                            onClick = {
                                send(viewModel, snackBarHostState, coroutineScope, successMessage)
                            },
                            enabled = !isSendingData // Button is enabled only when not sending data
                        )
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState) { data ->
                    Snackbar(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = data.message,
                            fontSize = 32.sp
                        )
                    }
                }
            }
        )
    }

    private fun send(
        viewModel: ClockTestViewModel,
        snackbarHostState: SnackbarHostState,
        coroutineScope: CoroutineScope,
        successMessage: String
    ) {
        try {
            viewModel.sendToServer(
                onSuccess = {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(successMessage)
                    }
                }, onFailure = { error ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(error.toString())
                    }
                })
        } catch (error: Exception) {
            println(error.message)
            coroutineScope.launch {
                snackbarHostState.showSnackbar(error.toString())
            }
        }
    }
}