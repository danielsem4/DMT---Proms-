package com.example.new_memory_test.presentation.screens.CallScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
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
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.new_memory_test.presentation.ViewModel.ViewModelMemoryTest
import com.example.new_memory_test.presentation.components.CircleWithPipeImage
import com.example.new_memory_test.presentation.components.dialogs.CustomDialog
import com.example.new_memory_test.presentation.screens.BaseTabletScreen
import com.example.new_memory_test.presentation.screens.InformScheduleScreen.effects.RipplePulseEffect
import com.example.new_memory_test.presentation.screens.RoomScreen.screen.RoomsScreens
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.backgroundColor
import org.example.hit.heal.core.presentation.primaryColor


class CallScreen(val pageNumber: Int )  : Screen {
    @Composable

    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        var showAcceptDialog by remember { mutableStateOf(false) }
        val viewModel: ViewModelMemoryTest = viewModel()


        BaseTabletScreen(title = stringResource(Resources.String.incoming_call_title), page = pageNumber, totalPages = 6) {

            Column(
                modifier = Modifier.Companion
                    .background(color = backgroundColor)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Companion.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Resources.String.incoming_call_text),
                    fontSize = 32.sp,
                    color = primaryColor,
                    fontWeight = FontWeight.Companion.Bold,
                    modifier = Modifier.Companion

                        .wrapContentHeight()
                        .padding(16.dp),
                    textAlign = TextAlign.Companion.Center
                )

                Text(
                    text = stringResource(Resources.String.call_from),
                    fontSize = 24.sp,
                    color = primaryColor,
                    fontWeight = FontWeight.Companion.Bold,
                    modifier = Modifier.Companion
                        .wrapContentHeight()
                        .padding(16.dp),
                    textAlign = TextAlign.Companion.Center
                )

                Text(
                    text = stringResource(Resources.String.phone_number),
                    fontSize = 24.sp,
                    color = primaryColor,
                    fontWeight = FontWeight.Companion.Bold,
                    modifier = Modifier.Companion
                        .wrapContentHeight()
                        .padding(24.dp),
                    textAlign = TextAlign.Companion.Center
                )

                Row(
                    modifier = Modifier.Companion
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp,
                        Alignment.Companion.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.Companion.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.Companion.size(120.dp),
                        contentAlignment = Alignment.Companion.Center
                    ) {
                        RipplePulseEffect(
                            modifier = Modifier.Companion.fillMaxSize(),
                            color = primaryColor
                        )

                        CircleWithPipeImage(
                            imagePainter = painterResource(resource = Resources.Icon.callAccept),
                            color = primaryColor,
                            onClick = {

                                viewModel.setAgree()
                                showAcceptDialog = true
                            }
                        )
                    }
                    Spacer(modifier = Modifier.Companion.width(200.dp))
                    Box(
                        modifier = Modifier.Companion.size(120.dp),
                        contentAlignment = Alignment.Companion.Center
                    ) {
                        RipplePulseEffect(
                            modifier = Modifier.Companion.fillMaxSize(),
                            color = Color.Companion.Red
                        )

                        CircleWithPipeImage(
                            imagePainter = painterResource(resource = Resources.Icon.callDecline),
                            color = Color.Companion.Red,
                            onClick = {
                                viewModel.setDisagree()
                                showAcceptDialog = true
                            }
                        )
                    }
                }
            }
        }

        if (showAcceptDialog) {
            CustomDialog(
                onDismiss = { showAcceptDialog = false },
                icon = {
                    Icon(
                        Icons.Default.ThumbUp,
                        contentDescription = null,
                        tint = Color.Companion.White,
                        modifier = Modifier.Companion.size(40.dp)
                    )
                },
                title = stringResource(Resources.String.thank_you_title),
                description = stringResource(Resources.String.thank_you_description),
                buttons = listOf(
                    stringResource(Resources.String.next) to {
                        showAcceptDialog = false
                        viewModel.setPage(viewModel.txtMemoryPage + 1)
                        navigator.push(RoomsScreens(pageNumber = 4))
                    }
                )
            )
        }
    }

}