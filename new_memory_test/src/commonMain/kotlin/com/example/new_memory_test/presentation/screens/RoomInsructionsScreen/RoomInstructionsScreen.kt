package com.example.new_memory_test.presentation.screens.RoomInsructionsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.new_memory_test.presentation.screens.BaseTabletScreen
import com.example.new_memory_test.presentation.screens.RoomScreen.components.enum_room.Room
import com.example.new_memory_test.presentation.screens.RoomScreen.screen.RoomsScreens
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.FontSize
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.White
import org.example.hit.heal.core.presentation.backgroundColor
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class RoomInstructionsScreen(val pageNumber: Int) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        var selectedRoom by remember { mutableStateOf(Room.Bedroom) }
        val roomButtons = Room.values().toList()


        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            BaseTabletScreen(
                title = stringResource(Resources.String.room_title),
                page = pageNumber,
                totalPages = 6,
                modifier = Modifier.Companion.fillMaxSize().background(color = backgroundColor)
            ) {

                Row(
                    modifier = Modifier.Companion
                        .fillMaxSize()
                        .background(color = backgroundColor)
                ) {
                    Column(
                        modifier = Modifier.Companion
                            .weight(0.4f)
                            .zIndex(1f)
                            .background(color = backgroundColor)
                            .padding(12.dp)
                    ) {
                        Box(
                            modifier = Modifier.Companion
                                .weight(0.4f)
                                .background(Color.Companion.White, RoundedCornerShape(8.dp))
                                .border(
                                    1.dp,
                                    Color.Companion.Black,
                                    androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                                )
                                .padding(vertical = 7.dp, horizontal = 7.dp)
                        ) {
                            Text(
                                text = stringResource(Resources.String.instructions_text_memory_question_trial),
                                fontSize =EXTRA_MEDIUM,
                                textAlign = TextAlign.Companion.Center,
                                fontWeight = FontWeight.Companion.Bold,
                                color = primaryColor,
                                modifier = Modifier.Companion.padding(bottom = 16.dp)
                            )
                        }

                        Spacer(modifier = Modifier.Companion.weight(0.3f))
                        Row(
                            modifier = Modifier.Companion.fillMaxWidth().padding(top = 16.dp)
                                .weight(0.4f),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            roomButtons.forEach { room ->
                                val isSelected = selectedRoom == room
                                Button(
                                    onClick = { selectedRoom = room },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = if (isSelected) Color.Companion.Gray else primaryColor
                                    ),
                                    shape = androidx.compose.foundation.shape.RoundedCornerShape(30),
                                    modifier = Modifier.Companion.height(50.dp).width(150.dp)
                                ) {
                                    Text(
                                        text = stringResource(room.displayName),
                                        color = if (isSelected) Color.Companion.Black else White,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.Companion.weight(0.2f))

                        Column(
                            modifier = Modifier.Companion
                                .weight(0.3f)
                                .zIndex(1f)
                                .padding(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Button(
                                    onClick = {
                                        navigator.push(RoomsScreens(pageNumber = 2))
                                    },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                                    shape = RoundedCornerShape(30),
                                    modifier = Modifier
                                        .defaultMinSize(minWidth = 100.dp)
                                        .width(250.dp)
                                        .height(50.dp)
                                ) {
                                    Text(
                                        text = stringResource(Resources.String.next),
                                        fontSize = EXTRA_MEDIUM,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }


                    }
                    Box(
                        modifier = Modifier.Companion
                            .weight(0.6f)
                            .fillMaxHeight()

                    ) {
                        Image(
                            painter = painterResource(selectedRoom.imageRes),
                            contentDescription = null,
                            modifier = Modifier.Companion
                                .fillMaxSize()
                                .zIndex(0f),
                            contentScale = ContentScale.Companion.Crop
                        )
                    }

                }
            }
        }
        RegisterBackHandler(this)
        {
            navigator.popUntilRoot()
        }
    }

}