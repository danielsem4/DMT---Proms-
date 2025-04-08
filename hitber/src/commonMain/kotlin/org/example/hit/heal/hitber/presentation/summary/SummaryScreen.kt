package org.example.hit.heal.hitber.presentation.summary

import TabletBaseScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.summary_hitbear_exit
import dmt_proms.hitber.generated.resources.summary_hitbear_instructions1
import dmt_proms.hitber.generated.resources.summary_hitbear_instructions2
import dmt_proms.hitber.generated.resources.summary_hitbear_title
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.presentation.timeAndPlace.TimeAndPlace
import org.jetbrains.compose.resources.stringResource

class SummaryScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        TabletBaseScreen(
            title = stringResource(Res.string.summary_hitbear_title),
            onNextClick = { navigator?.push(TimeAndPlace()) },
            question = 10,
            buttonText = stringResource(Res.string.summary_hitbear_exit),
            buttonColor = primaryColor,
            content = {

                Column(
                    modifier = Modifier.fillMaxWidth().padding(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                    Text(stringResource(Res.string.summary_hitbear_instructions1), fontSize = 25.sp)
                    Text(stringResource(Res.string.summary_hitbear_instructions2), fontSize = 25.sp)
                }
            })
    }
}
