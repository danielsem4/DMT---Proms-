package org.example.hit.heal.hitber.presentation.summary

import org.example.hit.heal.core.presentation.components.HorizontalTabletBaseScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.hit.heal.core.presentation.Resources.String.exit
import org.example.hit.heal.core.presentation.Resources.String.summaryHitberInstructions1
import org.example.hit.heal.core.presentation.Resources.String.summaryHitberInstructions2
import org.example.hit.heal.core.presentation.Resources.String.summaryHitberTitle
import org.example.hit.heal.core.presentation.utils.animations.SuccessAnimation
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


class SummaryScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel : ActivityViewModel = koinViewModel()

        HorizontalTabletBaseScreen(
            title = stringResource(summaryHitberTitle),
            onNextClick = { viewModel.uploadEvaluationResults()
                navigator?.pop()
               },
            question = 10,
            buttonText = stringResource(exit),
            buttonColor = primaryColor,
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
            })
    }
}


