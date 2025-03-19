package org.example.hit.heal.hitber.naming

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import org.example.hit.heal.core.presentation.BaseScreen
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
       // val viewModel: ActivityViewModel = viewModel()

        var answer1 by remember { mutableStateOf("") }
        var answer2 by remember { mutableStateOf("") }
        val selectedCouple by viewModel.selectedCouple.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.setRandomCouple()
        }


        BaseScreen(title = "שיום", onPrevClick = null, onNextClick = null, content = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                        horizontalArrangement = Arrangement.spacedBy(200.dp),
                        modifier = Modifier.padding(bottom = 20.dp)
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
                        horizontalArrangement = Arrangement.spacedBy(40.dp)) {

                        selectedCouple?.let { (firstImage, secondImage) ->
                            Image(
                                painter = painterResource(firstImage),
                                contentDescription = "First Image",
                                modifier = Modifier.size(400.dp).background(color = Color.White)
                            )
                            Image(
                                painter = painterResource(secondImage),
                                contentDescription = "Second Image",
                                modifier = Modifier.size(400.dp).background(color = Color.White)
                            )
                        }

                    }
                }
                Button(
                    modifier = Modifier.width(200.dp).align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                    onClick = { viewModel.setAnswersNaming(answer1, answer2)
                        navigator?.push(RepetitionScreen()) },
                    colors = ButtonDefaults.buttonColors(primaryColor),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        "המשך", color = Color.White, fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "4/10",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                )
            }


        })

    }
}

