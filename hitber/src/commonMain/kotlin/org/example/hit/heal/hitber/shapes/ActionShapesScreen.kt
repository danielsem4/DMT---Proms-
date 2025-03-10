package org.example.hit.heal.hitber.shapes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import dmt_proms.hitber.generated.resources.error_icon
import org.example.hit.heal.core.presentation.BaseScreen
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.concentration.ConcentrationScreen
import org.example.hit.heal.hitber.shapes.components.DialogTask
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel


class ActionShapesScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: ActivityViewModel = koinViewModel()

        val navigator = LocalNavigator.current
        val selectedShapes by viewModel.selectedShapes.collectAsState()
        val isFinished by viewModel.isFinished.collectAsState()
        var showDialog by remember { mutableStateOf(false) }
        val listShapes by viewModel.listShapes.collectAsState()

        BaseScreen(title = "צורות", onPrevClick = null, onNextClick = null, content = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "לפניך מספר צורות, עליך לבחור את 5 הצורות שראית לפני מספר דקות באמצעות לחיצה עליהן, אם אינך זוכר אפשר לנחש. בסיןם לחץ על המשך.",
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
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(5),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(listShapes) { shapeRes ->
                                val isSelected = selectedShapes.contains(shapeRes)
                                val shapeColor = if (isSelected) primaryColor else Color.Transparent

                                Image(
                                    painter = painterResource(shapeRes),
                                    contentDescription = "Shape",
                                    modifier = Modifier.size(100.dp)
                                        .padding(10.dp)
                                        .background(shapeColor)
                                        .clickable {
                                            viewModel.setSelectedShapes(shapeRes)
                                        }
                                )
                            }
                        }
                    }

                }
                Button(
                    modifier = Modifier
                        .width(200.dp)
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                    onClick = {
                        viewModel.calculateCorrectShapesCount()
                        viewModel.updateTask()
                        viewModel.resetSelectedShapes()
                        showDialog = true

                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF6FCF97)),
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


        })
        if (showDialog) {
            DialogTask(
                icon = Res.drawable.error_icon,
                title = "בחר 5 צורות",
                text = "אנא בחר את 5 הצורות שראית לפני מספר שאלות",
                onDismiss = { showDialog = false })
        }
        LaunchedEffect(isFinished) {
            if (isFinished) {
                navigator?.push(ConcentrationScreen())
                viewModel.setIsFinished(false)

            }
        }
    }
}

