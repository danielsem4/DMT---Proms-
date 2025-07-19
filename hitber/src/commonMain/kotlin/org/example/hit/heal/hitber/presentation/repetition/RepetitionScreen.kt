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
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.Resources.String.`continue`
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.hitber.presentation.understanding.UnderstandingScreen
import org.jetbrains.compose.resources.stringResource

class RepetitionScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current

        BaseScreen(title = "חזרה על משפט",
            config = ScreenConfig.TabletConfig,
            topRightText = "5/10",
            content = {
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
                        colors = ButtonDefaults.buttonColors(primaryColor),
                        shape = RoundedCornerShape(50)){
                        Text(
                            "הקשב", color = Color.White, fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(onClick = {},
                        colors = ButtonDefaults.buttonColors(primaryColor),
                        shape = RoundedCornerShape(50)){
                        Text(
                            "דבר", color = Color.White, fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }


                }
                Box(modifier = Modifier.fillMaxSize()) {
                    RoundedButton(
                        text = stringResource(`continue`),
                        modifier = Modifier.align(Alignment.BottomCenter).width(200.dp),
                        onClick = {
                            navigator?.replace(UnderstandingScreen())
                        }
                    )
                }
            }
        })

        RegisterBackHandler(this) {
            navigator?.pop()
        }
    }
}

