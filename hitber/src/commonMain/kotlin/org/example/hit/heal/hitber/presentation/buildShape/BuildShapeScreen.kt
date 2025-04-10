package org.example.hit.heal.hitber.presentation.buildShape

import TabletBaseScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.hitbear_continue
import dmt_proms.hitber.generated.resources.tenth_question_hitbear_instructions
import dmt_proms.hitber.generated.resources.tenth_question_hitbear_shape_image
import dmt_proms.hitber.generated.resources.tenth_question_hitbear_shape_model
import dmt_proms.hitber.generated.resources.tenth_question_hitbear_shapes
import dmt_proms.hitber.generated.resources.tenth_question_hitbear_title
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.presentation.buildShape.components.shapesItem
import org.example.hit.heal.hitber.presentation.summary.SummaryScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class BuildShapeScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val density = LocalDensity.current
        val viewModel: ActivityViewModel = viewModel()
        var screenSize by remember { mutableStateOf(0f to 0f) }


        TabletBaseScreen(
            title = stringResource(Res.string.tenth_question_hitbear_title),
            onNextClick = { navigator?.push(SummaryScreen())},
            buttonText = stringResource(Res.string.hitbear_continue),
            question = 10,
            buttonColor = primaryColor,
            content = {
                Text(
                    stringResource(Res.string.tenth_question_hitbear_instructions),
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(bottom = 30.dp)
                )


                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(color = Color.White, shape = RoundedCornerShape(4)).onSizeChanged { size ->
                            screenSize = size.width.toFloat() to size.height.toFloat()})
                {
                    Box(
                        modifier = Modifier.align(Alignment.CenterEnd)
                            .fillMaxWidth(0.7f)
                            .fillMaxHeight()
                            .background(color = Color(0xFFB2FFFF), shape = RoundedCornerShape(4.dp))
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(30.dp),
                        horizontalArrangement =  Arrangement.SpaceBetween
                    ) {
                        Text(
                            stringResource(Res.string.tenth_question_hitbear_shape_model), color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            stringResource(Res.string.tenth_question_hitbear_shapes), color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    shapesItem.forEachIndexed { index, shape ->
                        val itemWidthPx =  screenSize.second * shape.width
                        val itemHeightPx = screenSize.second * shape.height
                        val xPx = screenSize.first * shape.xRatio
                        val yPx = screenSize.second * shape.yRatio

                        val itemWidthDp = with(density) { itemWidthPx.toDp() }
                        val itemHeightDp = with(density) { itemHeightPx.toDp() }
                        val xDp = with(density) { xPx.toDp() }
                        val yDp = with(density) { yPx.toDp() }

                        Box(
                            modifier = Modifier
                                .offset(
                                    x = xDp,
                                    y = yDp
                                )
                                .size(itemWidthDp, itemHeightDp))

                                {
                            Image(
                                painter = painterResource(shape.image),
                                contentDescription = stringResource(Res.string.tenth_question_hitbear_shape_image),
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.FillBounds
                            )
                        }
                    }

                    val rightSideShapes = shapesItem.mapIndexed { index, shape ->
                        shape.copy(
                            xRatio = 0.75f + 0.1f * index,
                            yRatio = 0.2f + (0.2f * index)
                        )
                    }
                    rightSideShapes.forEachIndexed { index, shape ->
                        val itemWidthPx =  screenSize.second * shape.width
                        val itemHeightPx = screenSize.second * shape.height
                        val xPx = screenSize.first * shape.xRatio
                        val yPx = screenSize.second * shape.yRatio

                        val itemWidthDp = with(density) { itemWidthPx.toDp() }
                        val itemHeightDp = with(density) { itemHeightPx.toDp() }
                        val xDp = with(density) { xPx.toDp() }
                        val yDp = with(density) { yPx.toDp() }

                        Box(
                            modifier = Modifier
                                .offset(x = xDp, y = yDp)
                                .size(itemWidthDp, itemHeightDp)
                        ) {
                            Image(
                                painter = painterResource(shape.image),
                                contentDescription = stringResource(Res.string.tenth_question_hitbear_shape_image),
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.FillBounds
                            )
                        }
                }

            }
    })
}}

