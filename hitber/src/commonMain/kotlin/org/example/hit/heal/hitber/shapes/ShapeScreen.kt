package org.example.hit.heal.hitber.shapes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import dmt_proms.hitber.generated.resources.profile
import org.example.hit.heal.core.presentation.BaseScreen
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.shapes.components.DialogTask
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

class ShapeScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val viewModel: ActivityViewModel = koinViewModel()

        var showDialog by remember { mutableStateOf(true) }
        val shapeSet by viewModel.selectedSet.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.setRandomShapeSet()
        }

        BaseScreen(title = "צורות", onPrevClick = null, onNextClick = null, content = {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .fillMaxHeight(0.8f)
                        .background(color = Color.White),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                )
                {
                    shapeSet.forEach { shapeRes ->
                        Image(
                            painter = painterResource(shapeRes),
                            contentDescription = "Shape",
                            modifier = Modifier.size(150.dp)
                        )
                    }
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
            DialogTask(
                icon = Res.drawable.profile,
                title = "משימה",
                text = "בפניך 5 צורות, יש לזכור אותן להמשך המשימה בסיום לחץ על המשך",
                onDismiss = { showDialog = false })
        }
    }

}



