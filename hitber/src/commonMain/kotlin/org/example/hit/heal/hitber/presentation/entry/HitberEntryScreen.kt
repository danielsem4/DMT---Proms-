package org.example.hit.heal.hitber.presentation.entry

import org.example.hit.heal.core.presentation.components.HorizontalTabletBaseScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.BackPressHandler
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
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.core.presentation.Resources.Icon.chicken
import org.example.hit.heal.core.presentation.components.BaseYesNoDialog
import org.example.hit.heal.hitber.presentation.timeAndPlace.TimeAndPlace
import org.jetbrains.compose.resources.stringResource

class HitberEntryScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        var showBackDialog by remember { mutableStateOf(false) }

        BackPressHandler {
            showBackDialog = true
        }

        if (showBackDialog) {
            BaseYesNoDialog(
                onDismissRequest = { showBackDialog = false },
                icon = chicken,
                title = "אישור חזרה",
                message = "מעבר אחורה יחזור למסך הבית",
                confirmButtonText = "כן",
                onConfirm = {
                    showBackDialog = false
                    navigator?.pop()
                },
                dismissButtonText = "לא",
                onDismissButtonClick = { showBackDialog = false }
            )
        }


        HorizontalTabletBaseScreen(
            title = stringResource(entryHitberTitle),
            onNextClick = { navigator?.replace(TimeAndPlace()) },
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
