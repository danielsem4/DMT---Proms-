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
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.entry_hitbear_good_luck
import dmt_proms.hitber.generated.resources.entry_hitbear_instructions1
import dmt_proms.hitber.generated.resources.entry_hitbear_instructions2
import dmt_proms.hitber.generated.resources.entry_hitbear_instructions3
import dmt_proms.hitber.generated.resources.entry_hitbear_instructions4
import dmt_proms.hitber.generated.resources.entry_hitbear_instructions5
import dmt_proms.hitber.generated.resources.entry_hitbear_instructions6
import dmt_proms.hitber.generated.resources.entry_hitbear_instructions7
import dmt_proms.hitber.generated.resources.entry_hitbear_note
import dmt_proms.hitber.generated.resources.entry_hitbear_title
import dmt_proms.hitber.generated.resources.hitbear_start
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.hitber.presentation.timeAndPlace.TimeAndPlace
import org.jetbrains.compose.resources.stringResource

class EntryScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        TabletBaseScreen(
            title = stringResource(Res.string.entry_hitbear_title),
            onNextClick = { navigator?.push(TimeAndPlace()) },
            question = 0,
            buttonText = stringResource(Res.string.hitbear_start),
            buttonColor = primaryColor,
            content = {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(stringResource(Res.string.entry_hitbear_note), color = Color.Red, fontSize = 40.sp)

                    Column(
                        modifier = Modifier.fillMaxWidth().padding(30.dp),
                        verticalArrangement = Arrangement.spacedBy(30.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(stringResource(Res.string.entry_hitbear_instructions1), fontSize = 30.sp)
                        Text(stringResource(Res.string.entry_hitbear_instructions2), fontSize = 30.sp)
                        Text(stringResource(Res.string.entry_hitbear_instructions3), fontSize = 30.sp)
                        Text(stringResource(Res.string.entry_hitbear_instructions4), fontSize = 30.sp)
                        Text(stringResource(Res.string.entry_hitbear_instructions5), fontSize = 30.sp)
                        Text(stringResource(Res.string.entry_hitbear_instructions6), fontSize = 30.sp)
                    }

                    Text(stringResource(Res.string.entry_hitbear_instructions7), fontSize = 25.sp)
                    Text(stringResource(Res.string.entry_hitbear_good_luck), fontSize = 25.sp)
                }
            })
    }
}
