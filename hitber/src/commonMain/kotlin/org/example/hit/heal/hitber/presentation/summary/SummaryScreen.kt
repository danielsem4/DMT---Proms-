package org.example.hit.heal.hitber.presentation.summary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Resources.String.exit
import org.example.hit.heal.core.presentation.Resources.String.summaryHitberInstructions1
import org.example.hit.heal.core.presentation.Resources.String.summaryHitberInstructions2
import org.example.hit.heal.core.presentation.Resources.String.summaryHitberTitle
import org.example.hit.heal.core.presentation.Sizes.paddingLg
import org.example.hit.heal.core.presentation.Sizes.paddingXl
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.utils.animations.SuccessAnimation
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class SummaryScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: ActivityViewModel = koinViewModel()
        val uploadStatus by viewModel.uploadStatus.collectAsState()
        val snackbarHostState = remember { SnackbarHostState() }
        val isUploadFinished = uploadStatus != null
        val successMessage = stringResource(Resources.String.sentSuccessfully)

        LaunchedEffect(uploadStatus) {
            uploadStatus?.let { result ->
                if (result.isSuccess) {
                    snackbarHostState.showSnackbar(successMessage)
                } else if (result.isFailure) {
                    val error = result.exceptionOrNull()
                    snackbarHostState.showSnackbar(error?.message ?: "העלאה נכשלה")
                }
            }
        }

        BaseScreen(
            title = stringResource(summaryHitberTitle),
            config = ScreenConfig.TabletConfig,
            topRightText = "10/10",
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingXl),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(paddingLg)
                    ) {
                        Text(stringResource(summaryHitberInstructions1), fontSize = 25.sp)
                        Text(stringResource(summaryHitberInstructions2), fontSize = 25.sp)
                        SuccessAnimation(modifier = Modifier.size(100.dp))
                    }

                    RoundedButton(
                        text = stringResource(exit),
                        modifier = Modifier.width(200.dp),
                        onClick = {
                            navigator?.pop()
                        },
                        enabled = isUploadFinished
                    )
                }
            },
            snackbarHostState = snackbarHostState
        )

        RegisterBackHandler(this) {
            navigator?.pop()
        }
    }

}


