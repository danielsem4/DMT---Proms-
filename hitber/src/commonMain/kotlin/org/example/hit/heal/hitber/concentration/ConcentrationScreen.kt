package org.example.hit.heal.hitber.concentration

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.hit.heal.core.presentation.BaseScreen
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.naming.NamingScreen


class ConcentrationScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        //val viewModel: ActivityViewModel = koinViewModel()
        val viewModel: ActivityViewModel = viewModel()

        val buttonVisible by viewModel.concentrationStartButtonIsVisible.collectAsState()
        val number by viewModel.number.collectAsState()
        val isFinished by viewModel.isFinished.collectAsState()
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            viewModel.startRandomNumberGeneration(coroutineScope)
        }

        BaseScreen(title = "ריכוז", onPrevClick = null, onNextClick = null, content = {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    "בדקה הקרובה יופיעו במרכז המסך מספרים, בכל פעם שתראה את הספרה 7 עליך ללחוץ על המסך. להתחלת המטלה יש ללחוץ על התחל. בסיום המטלה תופיע ההודעה תודה. לחץ על המשך",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 20.dp)

                )

                if (buttonVisible) {
                    Button(
                        modifier = Modifier.width(200.dp).padding(bottom = 20.dp),
                        onClick = { viewModel.concentrationStartButtonSetVisible(false) },
                        colors = ButtonDefaults.buttonColors(primaryColor),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(
                            "התחל", color = Color.White, fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (!buttonVisible) {
                    if (isFinished)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.8f)
                                .background(color = Color.White)
                        ) {
                            Text(
                                "תודה, אנא עבור לשאלה הבאה", color = primaryColor, fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.Center))
                        }

                    else
                    RandomNumberScreen(
                        number = number,
                        onNumberClicked = { viewModel.addConcentrationAnswer(it) })

                }


                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Button(
                        modifier = Modifier.width(200.dp).align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp),
                        onClick = { if (isFinished) navigator?.push(NamingScreen()) },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (isFinished) primaryColor else Color.Gray
                        ),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(
                            "המשך", color = Color.White, fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "3/10",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                    )
                }


            }

        })

    }
}


@Composable
fun RandomNumberScreen(number: Int, onNumberClicked: (Int) -> Unit) {
    var isClicked by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
            .background(if (isClicked) primaryColor else Color.White)
            .clickable {
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

