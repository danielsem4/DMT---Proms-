package org.example.hit.heal.hitber.naming

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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.repetition.RepetitionScreen
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

class NamingScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val viewModel: ActivityViewModel = koinViewModel()
        var answer1 by remember { mutableStateOf("") }
        var answer2 by remember { mutableStateOf("") }
        val selectedCouple by viewModel.selectedCouple.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.setRandomCouple()
        }

        TabletBaseScreen(title = "שיום",
            onNextClick = {
                viewModel.setAnswersNaming(answer1, answer2)
                navigator?.push(RepetitionScreen())
            },
            question = 4,
            buttonText = "המשך",
            buttonColor = primaryColor,
            content = {
                Text(
                    "כתוב מה מוצג בתמונה. לכתיבת התשובה יש ללחוץ על השאלה. בסיום לחץ על המשך",
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
                        label = { Text("מה מופיע בתמונה?", color = Color.Black) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = primaryColor,
                            unfocusedBorderColor = primaryColor
                        ),
                        shape = RoundedCornerShape(0.dp)
                    )

                    TextField(
                        value = answer2,
                        onValueChange = { answer2 = it },
                        label = { Text("מה מופיע בתמונה?", color = Color.Black) },
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
                            contentDescription = "First Image",
                            modifier = Modifier.weight(1f).fillMaxHeight().padding(20.dp)
                                .background(color = Color.White)
                        )
                        Image(
                            painter = painterResource(secondImage),
                            contentDescription = "Second Image",
                            modifier = Modifier.weight(1f).fillMaxHeight().padding(20.dp)
                                .background(color = Color.White)
                        )
                    }

                }
            })
    }
}

