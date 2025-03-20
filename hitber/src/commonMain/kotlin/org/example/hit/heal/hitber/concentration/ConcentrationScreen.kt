package org.example.hit.heal.hitber.concentration

import TabletBaseScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.naming.NamingScreen
import org.koin.compose.viewmodel.koinViewModel


class ConcentrationScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val viewModel: ActivityViewModel = koinViewModel()
        val buttonVisible by viewModel.startButtonIsVisible.collectAsState()
        val number by viewModel.number.collectAsState()
        val isFinished by viewModel.isFinished.collectAsState()
        val isNumberClickable by viewModel.isNumberClickable.collectAsState()

        TabletBaseScreen(
            title = "ריכוז",
            onNextClick = { if (isFinished) navigator?.push(NamingScreen()) },
            question = 3,
            buttonText = "המשך",
            buttonColor = if (isFinished) primaryColor else Color.Gray,
            content = {
                Text(
                    "בדקה הקרובה יופיעו במרכז המסך מספרים, בכל פעם שתראה את הספרה 7 עליך ללחוץ על המסך. להתחלת המטלה יש ללחוץ על התחל. בסיום המטלה תופיע ההודעה תודה. לחץ על המשך",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 20.dp)
                )

                if (buttonVisible) {
                    Button(
                        modifier = Modifier.width(300.dp).padding(bottom = 20.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            viewModel.startButtonSetVisible(false)
                            viewModel.startRandomNumberGeneration()
                        },
                        colors = ButtonDefaults.buttonColors(primaryColor),
                        shape = RoundedCornerShape(30)
                    ) {
                        Text(
                            "התחל", color = Color.White, fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )

                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.White)
                    )
                } else {
                    if (isFinished) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color.White)
                        ) {
                            Text(
                                "תודה, אנא עבור לשאלה הבאה", color = primaryColor, fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    } else
                        RandomNumberScreen(
                            number = number,
                            isClickable = isNumberClickable,
                            onNumberClicked = { viewModel.setAnswersConcentration(it) })
                }
            })
    }
}

@Composable
fun RandomNumberScreen(number: Int, isClickable: Boolean, onNumberClicked: (Int) -> Unit) {
    var isClicked by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isClicked) primaryColor else Color.White)
            .clickable(enabled = isClickable) {
                isClicked = true
                onNumberClicked(number)
                coroutineScope.launch {
                    delay(100)
                    isClicked = false
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = if (isClicked) Color.White else primaryColor
        )
    }
}

