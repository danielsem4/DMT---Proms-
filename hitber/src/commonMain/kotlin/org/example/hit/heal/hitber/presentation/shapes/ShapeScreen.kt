package org.example.hit.heal.hitber.presentation.shapes

import TabletBaseScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.profile
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.presentation.shapes.components.DialogTask
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

        TabletBaseScreen(
            title = "צורות",
            onNextClick = { navigator?.push(ActionShapesScreen(2)) },
            question = 2,
            buttonText = "המשך", buttonColor = primaryColor,
            content = {

                Row(
                    modifier = Modifier.fillMaxSize()
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



