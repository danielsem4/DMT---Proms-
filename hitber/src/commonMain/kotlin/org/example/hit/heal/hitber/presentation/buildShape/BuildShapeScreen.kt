package org.example.hit.heal.hitber.presentation.buildShape

import TabletBaseScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import org.example.hit.heal.core.presentation.Colors.primaryColor

class BuildShapeScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current

        TabletBaseScreen(
            title = "בנה צורה",
            onNextClick = {},
            buttonText = "המשך",
            question = 10,
            buttonColor = primaryColor,
            content = {
                Text(
                    "עליך להעתיק את התמונה שמוצגת בצד שמאל, למרכז המסך. את התמונה יש להעתיק באמצעות הצורות המופיעות בצד ימין. את הצורות צריך לגרור ולסדר במרכז המסך.",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(bottom = 30.dp)
                )


                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(color = Color.White, shape = RoundedCornerShape(4))
                )
                {
                    Box(
                        modifier = Modifier.align(Alignment.CenterEnd)
                            .fillMaxWidth(0.7f)
                            .fillMaxHeight()
                            .background(color = Color(0xFFB2FFFF), shape = RoundedCornerShape(4.dp))
                    )

                }
            })
    }
}

