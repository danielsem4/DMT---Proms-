package org.example.hit.heal.hitber.presentation.naming

import TabletBaseScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.getCurrentFormattedDateTime
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.fourth_question_hitbear_instructions
import dmt_proms.hitber.generated.resources.fourth_question_hitbear_pic1
import dmt_proms.hitber.generated.resources.fourth_question_hitbear_pic2
import dmt_proms.hitber.generated.resources.fourth_question_hitbear_title
import dmt_proms.hitber.generated.resources.fourth_question_hitbear_what_in_the_pic
import dmt_proms.hitber.generated.resources.hitbear_continue
import getImageName
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.presentation.naming.components.NamingImages
import org.example.hit.heal.hitber.presentation.naming.components.NamingTextFields
import org.example.hit.heal.hitber.presentation.repetition.RepetitionScreen
import org.example.hit.heal.hitber.utils.InstructionText
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class NamingScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val fourthQuestionViewModel: FourthQuestionViewModel = koinViewModel()
        val viewModel: ActivityViewModel = koinViewModel()
        val answer1 by fourthQuestionViewModel.answer1.collectAsState()
        val answer2 by fourthQuestionViewModel.answer2.collectAsState()
        val selectedCouple by fourthQuestionViewModel.selectedCouple.collectAsState()

        val firstImageName = selectedCouple?.let { getImageName(it.first) } ?: ""
        val secondImageName = selectedCouple?.let { getImageName(it.second) } ?: ""

        TabletBaseScreen(title = stringResource(Res.string.fourth_question_hitbear_title),
            onNextClick = {
                fourthQuestionViewModel.fourthQuestionAnswer(
                    firstImageName,
                    secondImageName
                )
                viewModel.setFourthQuestion(
                    fourthQuestionViewModel.fourthQuestionAnswers,
                    getCurrentFormattedDateTime()
                )
                navigator?.push(RepetitionScreen())
            },
            question = 4,
            buttonText = stringResource(Res.string.hitbear_continue),
            buttonColor = primaryColor,
            content = {
                InstructionText(stringResource(Res.string.fourth_question_hitbear_instructions))
                NamingTextFields(answer1, answer2, fourthQuestionViewModel)
                NamingImages(selectedCouple)
            })
    }
}

