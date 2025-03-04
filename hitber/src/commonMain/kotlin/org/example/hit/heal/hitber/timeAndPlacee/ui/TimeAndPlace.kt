package org.example.hit.heal.hitber.timeAndPlacee.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import org.example.hit.heal.hitber.shapes.ShapeScreen
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.questions.FifthQuestion
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.questions.FirstQuestion
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.questions.ForthQuestion
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.questions.SecondQuestion
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.questions.SeventhQuestion
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.questions.SixthQuestion
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.questions.ThirdQuestion

class TimeAndPlace : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        BaseScreen(title = "זמן ומקום", onPrevClick = null, onNextClick = null, content = {

            Box(
                modifier = Modifier.fillMaxSize() // Box תופס את כל שטח המסך
            ) {

                Text(
                    "בחר את התשובה הנכונה. לבחירת התשובה הנכונה יש ללחוץ על השאלה",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter)
                )
                Column(
                    modifier = Modifier.padding(top = 100.dp)
                        .fillMaxHeight(0.8f)
                        .padding(horizontal = 16.dp).verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    // שאלות בתוך ה-Column
                    FirstQuestion()
                    SecondQuestion()
                    ThirdQuestion()
                    ForthQuestion()
                    FifthQuestion()
                    SixthQuestion()
                    SeventhQuestion()
                }

                // כפתור בתחתית המסך
                Button(
                    modifier = Modifier
                        .width(200.dp)
                        .align(Alignment.BottomCenter) // ממקם את הכפתור בתחתית
                        .padding(bottom = 16.dp),
                    onClick = { navigator?.push(ShapeScreen()) },
                    colors = ButtonDefaults.buttonColors(primaryColor),
                    shape = RoundedCornerShape(50)
                ) {
                    Text("הבא", color = Color.Black, fontSize = 15.sp)
                }
            }
        })
    }

}

