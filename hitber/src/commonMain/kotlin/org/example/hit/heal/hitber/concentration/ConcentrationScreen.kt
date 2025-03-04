package org.example.hit.heal.hitber.concentration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import org.example.hit.heal.hitber.naming.NamingScreen

class ConcentrationScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current

        BaseScreen(title = "ריכוז", onPrevClick = null, onNextClick = null, content = {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    "בדקה הקרובה יופיעו במרכז המסך מספרים, בכל פעם שתראה את הספרה 7 עליך ללחוץ על המסך. להתחלת המטלה יש ללחוץ על התחל",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 20.dp)

                )
                Button(
                    modifier = Modifier.width(200.dp).padding(bottom = 20.dp),
                    onClick = { navigator?.push(NamingScreen())},
                    colors = ButtonDefaults.buttonColors(Color(0xFF6FCF97)),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        "התחל", color = Color.White, fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f)
                        .background(color = Color.White, shape = RoundedCornerShape(4))
                )
                {

                }


            }

        })

    }
}

