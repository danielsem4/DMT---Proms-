package org.example.hit.heal.hitber.understanding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.close_fridge
import dmt_proms.hitber.generated.resources.open_fridge
import dmt_proms.hitber.generated.resources.peas
import dmt_proms.hitber.generated.resources.table
import org.example.hit.heal.core.presentation.BaseScreen
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.dragAndDrop.DragAndDropScreen
import org.jetbrains.compose.resources.painterResource

class UnderstandingScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        var isClicked by remember { mutableStateOf(true) }


        BaseScreen(title = "הבנת הוראות", onPrevClick = null, onNextClick = null, content = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "בחלק זה תתבקש לבצע מטלה. לשמיעת המטלה לחץ על הקשב. בסיום לחץ על המשך.",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                            .padding(bottom = 30.dp)
                    )

                    Button(
                        onClick = {}, modifier = Modifier,
                        colors = ButtonDefaults.buttonColors(Color(0xFF6FCF97)),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(
                            "הקשב", color = Color.White, fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .fillMaxHeight(0.8f)
                            .background(color = Color.White, shape = RoundedCornerShape(4))
                    )
                    {



                            Image(
                                painter = if (isClicked) {
                                    painterResource(Res.drawable.open_fridge)
                                } else {
                                    painterResource(Res.drawable.close_fridge)
                                },
                                contentDescription = if (isClicked) "open fridge" else "close fridge",
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(10.dp)
                                    .align(Alignment.CenterStart)
                                    .clickable {
                                        isClicked = !isClicked
                                    }
                            )

                            if (isClicked) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()

                                ) {
                                    Image(
                                        painter = painterResource(Res.drawable.peas),
                                        contentDescription = "Food Item 1",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .offset(x = 25.dp, y = 110.dp) // מיקום ספציפי במקרר
                                    )
                                }}

                            Image(
                                painter = painterResource(Res.drawable.table),
                                contentDescription = "table",
                                modifier = Modifier.fillMaxHeight()
                                    .align(Alignment.CenterEnd)

                            )
                        }


                }
                Button(
                    modifier = Modifier.width(200.dp).align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                    onClick = { navigator?.push(DragAndDropScreen()) },
                    colors = ButtonDefaults.buttonColors(primaryColor),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        "המשך", color = Color.White, fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "6/10",
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

