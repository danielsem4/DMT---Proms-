package org.example.hit.heal.hitber.presentation.entry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.FontSize.EXTRA_LARGE
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Resources.String.entryHitberGoodLuck
import org.example.hit.heal.core.presentation.Resources.String.entryHitberInstructions1
import org.example.hit.heal.core.presentation.Resources.String.entryHitberInstructions2
import org.example.hit.heal.core.presentation.Resources.String.entryHitberInstructions3
import org.example.hit.heal.core.presentation.Resources.String.entryHitberInstructions4
import org.example.hit.heal.core.presentation.Resources.String.entryHitberInstructions5
import org.example.hit.heal.core.presentation.Resources.String.entryHitberInstructions6
import org.example.hit.heal.core.presentation.Resources.String.entryHitberInstructions7
import org.example.hit.heal.core.presentation.Resources.String.entryHitberNote
import org.example.hit.heal.core.presentation.Resources.String.entryHitberTitle
import org.example.hit.heal.core.presentation.Resources.String.start
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.timeAndPlace.TimeAndPlace
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class HitBerEntryScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: ActivityViewModel = koinViewModel()

        BaseScreen(
            title = stringResource(entryHitberTitle),
            config = ScreenConfig.TabletConfig,
            topRightText = "0/10",
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            stringResource(entryHitberNote),
                            color = Color.Red,
                            fontSize = EXTRA_LARGE
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(30.dp)
                        ) {
                            Text(stringResource(entryHitberInstructions1), fontSize = LARGE)
                            Text(stringResource(entryHitberInstructions2), fontSize = LARGE)
                            Text(stringResource(entryHitberInstructions3), fontSize = LARGE)
                            Text(stringResource(entryHitberInstructions4), fontSize = LARGE)
                            Text(stringResource(entryHitberInstructions5), fontSize = LARGE)
                            Text(stringResource(entryHitberInstructions6), fontSize = LARGE)
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(30.dp)
                        ) {
                            Text(stringResource(entryHitberInstructions7), fontSize = 25.sp)
                            Text(stringResource(entryHitberGoodLuck), fontSize = 25.sp)
                        }
                    }

                    RoundedButton(
                        text = stringResource(start),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(200.dp),
                        onClick = {
                            navigator?.replace(TimeAndPlace())
                        }
                    )
                }
            }
        )


        RegisterBackHandler(this)
        {
            navigator?.pop()
        }

        // Load evaluation data when the composable enters composition
        LaunchedEffect(Unit) {
            viewModel.loadEvaluation("HitBer")
        }
    }
}
