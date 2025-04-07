package org.example.hit.heal.hitber.presentation.buildShape

import TabletBaseScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import dmt_proms.hitber.generated.resources.hitbear_continue
import dmt_proms.hitber.generated.resources.ninth_question_hitbear_instructions
import dmt_proms.hitber.generated.resources.ninth_question_hitbear_title
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.jetbrains.compose.resources.stringResource

class BuildShapeScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val viewModel: ActivityViewModel = viewModel()

        TabletBaseScreen(
            title = stringResource(Res.string.ninth_question_hitbear_title),
            onNextClick = {},
            buttonText = stringResource(Res.string.hitbear_continue),
            question = 10,
            buttonColor = primaryColor,
            content = {
                Text(
                    stringResource(Res.string.ninth_question_hitbear_instructions),
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(bottom = 30.dp)
                )


                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(color = Color.White, shape = RoundedCornerShape(4))
                )
                {
                    Box(
                        modifier = Modifier.align(Alignment.CenterEnd)
                            .fillMaxWidth(0.7f)
                            .fillMaxHeight()
                            .background(color = Color(0xFFB2FFFF), shape = RoundedCornerShape(4.dp))
                    )

                }
            })
    }
}

