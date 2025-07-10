package org.example.hit.heal.hitber.presentation.summary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.Resources.String.exit
import org.example.hit.heal.core.presentation.Resources.String.summaryHitberInstructions1
import org.example.hit.heal.core.presentation.Resources.String.summaryHitberInstructions2
import org.example.hit.heal.core.presentation.Resources.String.summaryHitberTitle
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
        val viewModel : ActivityViewModel = koinViewModel()

        BaseScreen(
            title = stringResource(summaryHitberTitle),
            config = ScreenConfig.TabletConfig,
            topRightText = "10/10",
            content = {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(25.dp)
                    ) {
                    Text(stringResource(summaryHitberInstructions1), fontSize = 25.sp)
                    Text(stringResource(summaryHitberInstructions2), fontSize = 25.sp)
                    SuccessAnimation(modifier = Modifier.size(100.dp))
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    RoundedButton(
                        text = stringResource(exit),
                        modifier = Modifier.align(Alignment.BottomCenter).width(200.dp),
                        onClick = {
                            viewModel.uploadEvaluationResults(
                                onSuccess = {
                                    println("success")
                                            },
                                onFailure = {
                                    println("failure")
                                })
                            navigator?.pop()
                        }
                    )
                }
            })

        RegisterBackHandler(this) {
            navigator?.pop()
        }
    }
}


