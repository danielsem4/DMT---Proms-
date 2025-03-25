package org.example.hit.heal.hitber.presentation.shapes

import TabletBaseScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.error_icon
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.presentation.concentration.ConcentrationScreen
import org.example.hit.heal.hitber.presentation.shapes.components.DialogTask
import org.jetbrains.compose.resources.painterResource


class ActionShapesScreen : Screen {
    @Composable
    override fun Content() {

       // val viewModel: ActivityViewModel = koinViewModel()
        val viewModel: ActivityViewModel = viewModel()

        val navigator = LocalNavigator.current
        val selectedShapes by viewModel.selectedShapes.collectAsState()
        val attempt by viewModel.attempt.collectAsState()
        var showDialog by remember { mutableStateOf(false) }
        val listShapes by viewModel.listShapes.collectAsState()

        TabletBaseScreen(title = "צורות", onNextClick = {
            viewModel.calculateCorrectShapesCount()
            viewModel.updateTask()
            viewModel.setAnswersShapes()

            if (attempt < 3) {
                showDialog = true
            } else {
                navigator?.push(ConcentrationScreen())
            }

        }, question = 2, buttonText = "המשך", buttonColor = primaryColor, content = {
            Text(
                "לפניך מספר צורות, עליך לבחור את 5 הצורות שראית לפני מספר דקות באמצעות לחיצה עליהן, אם אינך זוכר אפשר לנחש. בסיום לחץ על המשך.",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(bottom = 30.dp)
            )

            Box(
                modifier = Modifier.fillMaxSize()
                    .background(Color.White, shape = RoundedCornerShape(4))
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(40.dp)
                ) {
                    val chunkedShapes = listShapes.chunked(5)

                    chunkedShapes.forEach { rowShapes ->
                        Row(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(40.dp)
                        ) {
                            rowShapes.forEach { shapeRes ->
                                val isSelected = selectedShapes.contains(shapeRes)
                                val shapeColor = if (isSelected) primaryColor else Color.Transparent

                                Image(
                                    painter = painterResource(shapeRes),
                                    contentDescription = "Shape",
                                    modifier = Modifier
                                        .background(shapeColor).weight(1f)
                                        .clickable { viewModel.setSelectedShapes(shapeRes) }
                                )
                            }
                        }
                    }
                }
            }

        })
        if (showDialog) {
            DialogTask(
                icon = Res.drawable.error_icon,
                title = "בחר 5 צורות",
                text = "אנא בחר את 5 הצורות שראית לפני מספר שאלות",
                onDismiss = { showDialog = false })
        }

    }
}

