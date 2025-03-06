package org.example.hit.heal.hitber.shapes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.profile
import org.example.hit.heal.core.presentation.BaseScreen
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.jetbrains.compose.resources.painterResource

class ShapeScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        var showDialog by remember { mutableStateOf(true) }

        BaseScreen(title = "צורות", onPrevClick = null, onNextClick = null, content = {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .fillMaxHeight(0.8f).padding(top = 20.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(4))
                )
                {

                }
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Button(
                        modifier = Modifier.width(200.dp).align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp),
                        onClick = { navigator?.push(ActionShapesScreen()) },
                        colors = ButtonDefaults.buttonColors(primaryColor),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(
                            "המשך", color = Color.White, fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "2/10",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                    )
                }
            }

        })

        if (showDialog) {
            Dialog(onDismissRequest =  { showDialog = false }) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(Res.drawable.profile),
                        contentDescription = "profile icon",
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.TopCenter)
                            .zIndex(1f)
                    )
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = 8.dp,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 25.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "משימה",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = primaryColor,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "בפניך 5 צורות, יש לזכור אותן להמשך המשימה בסיום לחץ על המשך",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = primaryColor,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Button(
                                onClick = {   showDialog = false  },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),

                            ) {
                                Text("הבנתי", color = Color.White)
                            }
                        }
                    }
                }
            }
        }

    }
}

