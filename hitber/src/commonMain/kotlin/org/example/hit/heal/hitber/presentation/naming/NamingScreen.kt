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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
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
import org.example.hit.heal.hitber.presentation.repetition.RepetitionScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class NamingScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
       // val viewModel: ActivityViewModel = koinViewModel()
        val viewModel: ActivityViewModel = viewModel()
        var answer1 by remember { mutableStateOf("") }
        var answer2 by remember { mutableStateOf("") }
        val selectedCouple by viewModel.selectedCouple.collectAsState()

        val firstImageName = selectedCouple?.let { getImageName(it.first) } ?: ""
        val secondImageName = selectedCouple?.let { getImageName(it.second) } ?: ""

        LaunchedEffect(Unit) {
            viewModel.setRandomCouple()
        }

        TabletBaseScreen(title = stringResource(Res.string.fourth_question_hitbear_title),
            onNextClick = {
                viewModel.setAnswersNaming(answer1, answer2, firstImageName, secondImageName)
                navigator?.push(RepetitionScreen())
            },
            question = 4,
            buttonText = stringResource(Res.string.hitbear_continue),
            buttonColor = primaryColor,
            content = {
                Text(
                    stringResource(Res.string.fourth_question_hitbear_instructions),
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(bottom = 30.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
                ) {
                    TextField(
                        value = answer1,
                        onValueChange = { answer1 = it },
                        label = { Text(stringResource(Res.string.fourth_question_hitbear_what_in_the_pic), color = Color.Black) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = primaryColor,
                            unfocusedBorderColor = primaryColor
                        ),
                        shape = RoundedCornerShape(0.dp)
                    )

                    TextField(
                        value = answer2,
                        onValueChange = { answer2 = it },
                        label = { Text(stringResource(Res.string.fourth_question_hitbear_what_in_the_pic), color = Color.Black) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = primaryColor,
                            unfocusedBorderColor = primaryColor
                        ),
                        shape = RoundedCornerShape(0.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    selectedCouple?.let { (firstImage, secondImage) ->
                        Image(
                            painter = painterResource(firstImage),
                            contentDescription = stringResource(Res.string.fourth_question_hitbear_pic1),
                            modifier = Modifier.weight(1f).fillMaxHeight().padding(20.dp)
                                .background(color = Color.White)
                        )
                        Image(
                            painter = painterResource(secondImage),
                            contentDescription = stringResource(Res.string.fourth_question_hitbear_pic2),
                            modifier = Modifier.weight(1f).fillMaxHeight().padding(20.dp)
                                .background(color = Color.White)
                        )
                    }

                }
            })
    }
}

