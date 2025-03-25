package org.example.hit.heal.hitber.presentation.repetition

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import org.example.hit.heal.hitber.presentation.understanding.UnderstandingScreen

class RepetitionScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current

        BaseScreen(title = "חזרה על משפט", onPrevClick = null, onNextClick = null, content = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "יש ללחוץ על כפתור ההקשב ולהקשיב למשפט, לאחר מכן יש ללחוץ על כפתור הדבר ולחזור על המשפט הנאמר.",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                            .padding(bottom = 50.dp)
                    )

                    Button(onClick = {}, modifier = Modifier.padding(bottom = 30.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFF6FCF97)),
                        shape = RoundedCornerShape(50)){
                        Text(
                            "הקשב", color = Color.White, fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(onClick = {},
                        colors = ButtonDefaults.buttonColors(Color(0xFF6FCF97)),
                        shape = RoundedCornerShape(50)){
                        Text(
                            "דבר", color = Color.White, fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }


                }
                Button(
                    modifier = Modifier
                        .width(200.dp)
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                    onClick = { navigator?.push(UnderstandingScreen())},
                    colors = ButtonDefaults.buttonColors(Color(0xFF6FCF97)),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        "המשך", color = Color.White, fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


        })

    }
}

