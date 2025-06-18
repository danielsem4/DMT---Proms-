package org.example.hit.heal.hitber.presentation.entry

import TabletBaseScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
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
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.hitber.presentation.timeAndPlace.TimeAndPlace
import org.jetbrains.compose.resources.stringResource

class EntryScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        TabletBaseScreen(
            title = stringResource(entryHitberTitle),
            onNextClick = { navigator?.push(TimeAndPlace()) },
            question = 0,
            buttonText = stringResource(start),
            buttonColor = primaryColor,
            content = {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(stringResource(entryHitberNote), color = Color.Red, fontSize = 40.sp)

                    Column(
                        modifier = Modifier.fillMaxWidth().padding(30.dp),
                        verticalArrangement = Arrangement.spacedBy(30.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(stringResource(entryHitberInstructions1), fontSize = 30.sp)
                        Text(stringResource(entryHitberInstructions2), fontSize = 30.sp)
                        Text(stringResource(entryHitberInstructions3), fontSize = 30.sp)
                        Text(stringResource(entryHitberInstructions4), fontSize = 30.sp)
                        Text(stringResource(entryHitberInstructions5), fontSize = 30.sp)
                        Text(stringResource(entryHitberInstructions6), fontSize = 30.sp)
                    }

                    Text(stringResource(entryHitberInstructions7), fontSize = 25.sp)
                    Text(stringResource(entryHitberGoodLuck), fontSize = 25.sp)
                }
            })
    }
}
