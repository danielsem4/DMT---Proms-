package org.example.hit.heal.hitber.buildShape

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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

class BuildShapeScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current

        BaseScreen(title = "בנה צורה", onPrevClick = null, onNextClick = null, content = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "עליך להעתיק את התמונה שמוצגת בצד שמאל, למרכז המסך. את התמונה יש להעתיק באמצעות הצורות המופיעות בצד ימין. את הצורות צריך לגרור ולסדר במרכז המסך.",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                            .padding(bottom = 30.dp)
                    )


                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .fillMaxHeight(0.8f)
                            .background(color = Color.White, shape = RoundedCornerShape(4))
                    )
                    {
                        Box(
                            modifier = Modifier.align(Alignment.CenterEnd)
                                .fillMaxWidth(0.7f)
                                .fillMaxHeight()
                                .background(color = Color(0xFFB2FFFF), shape = RoundedCornerShape(4.dp)))

                    }


                }
                Button(
                    modifier = Modifier
                        .width(200.dp)
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                    onClick = { },
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

