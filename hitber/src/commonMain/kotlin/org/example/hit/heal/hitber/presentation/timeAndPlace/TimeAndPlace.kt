package org.example.hit.heal.hitber.presentation.timeAndPlace

import TabletBaseScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.first_question_hitbear_instructions
import dmt_proms.hitber.generated.resources.first_question_hitbear_title
import dmt_proms.hitber.generated.resources.hitbear_continue
import kotlinx.serialization.json.JsonNull.content
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.presentation.shapes.ShapeScreen
import org.example.hit.heal.hitber.presentation.timeAndPlace.components.Questions
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


class TimeAndPlace : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: ActivityViewModel = koinViewModel()
        val allAnswersFinished by viewModel.allAnswersFinished.collectAsState()

        TabletBaseScreen(
            title = stringResource(Res.string.first_question_hitbear_title),
            onNextClick = {
                if (allAnswersFinished) {
                    navigator?.push(ShapeScreen())
                }
            },
            question = 1,
            buttonText = stringResource(Res.string.hitbear_continue),
            buttonColor = if (allAnswersFinished) {
                primaryColor
            } else Color.Gray,
            content = {
                Text(
                    stringResource(Res.string.first_question_hitbear_instructions),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Column(
                    modifier = Modifier.padding(top = 30.dp)
                        .padding(horizontal = 16.dp).verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Questions(viewModel)
                }
            })

    }
}

