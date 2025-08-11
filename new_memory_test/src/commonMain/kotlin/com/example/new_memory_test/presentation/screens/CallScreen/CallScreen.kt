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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.new_memory_test.presentation.ViewModel.ViewModelMemoryTest
import com.example.new_memory_test.presentation.components.CircleWithPipeImage
import com.example.new_memory_test.presentation.screens.CallScreen.effects.RipplePulseEffect
import com.example.new_memory_test.presentation.screens.RoomScreen.screen.RoomsScreens
import org.example.hit.heal.core.presentation.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM_LARGE
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Resources.String.old_phone_ring
import org.example.hit.heal.core.presentation.Sizes.paddingLg
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.Sizes.widthMd
import org.example.hit.heal.core.presentation.backgroundColor
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.components.dialogs.CustomDialog
import org.example.hit.heal.core.presentation.primaryColor
import org.koin.compose.viewmodel.koinViewModel


class CallScreen(val pageNumber: Int )  : Screen {
    @Composable

    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        var showAcceptDialog by remember { mutableStateOf(false) }
        val viewModel: ViewModelMemoryTest = koinViewModel()

        val audioUrl = stringResource(old_phone_ring)
        var isAudioClicked by remember { mutableStateOf(true) }

        //Use audio (check resourses) - must started after open and stop in button
        LaunchedEffect(isAudioClicked) {
            if (isAudioClicked) {
                audioUrl.let {
                    viewModel.onPlayAudio(it)
                    isAudioClicked = false
                }
            }
        }

        //Save in one side and don't depend on language
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr){
        BaseScreen(title = stringResource(Resources.String.incoming_call_title),  config = ScreenConfig.TabletConfig,  topRightText = "$pageNumber/6", content =

           {

            Column(
                modifier = Modifier.Companion
                    .background(color = backgroundColor)
                    .padding(paddingMd),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Companion.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Resources.String.incoming_call_text),
                    fontSize = LARGE ,
                    color = primaryColor,
                    fontWeight = FontWeight.Companion.Bold,
                    modifier = Modifier.Companion

                        .wrapContentHeight()
                        .padding(paddingMd),
                    textAlign = TextAlign.Companion.Center
                )

                Text(
                    text = stringResource(Resources.String.call_from),
                    fontSize = EXTRA_MEDIUM_LARGE,
                    color = primaryColor,
                    fontWeight = FontWeight.Companion.Bold,
                    modifier = Modifier.Companion
                        .wrapContentHeight()
                        .padding(paddingMd),
                    textAlign = TextAlign.Companion.Center
                )

                Text(
                    text = stringResource(Resources.String.phone_number),
                    fontSize =EXTRA_MEDIUM_LARGE,
                    color = primaryColor,
                    fontWeight = FontWeight.Companion.Bold,
                    modifier = Modifier.Companion
                        .wrapContentHeight()
                        .padding(paddingLg),
                    textAlign = TextAlign.Companion.Center
                )

                Row(
                    modifier = Modifier.Companion
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(paddingLg),
                    horizontalArrangement = Arrangement.spacedBy(
                        spacingMd,
                        Alignment.Companion.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.Companion.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.Companion.size(widthMd),
                        contentAlignment = Alignment.Companion.Center
                    ) {

                        //Create animation for accept
                        RipplePulseEffect(
                            modifier = Modifier.Companion.fillMaxSize(),
                            color = primaryColor
                        )
                        CircleWithPipeImage(
                            imagePainter = painterResource(resource = Resources.Icon.callAccept),
                            color = primaryColor,
                            onClick = {
                                viewModel.stopAudio()
                                isAudioClicked = false
                                viewModel.setPhoneResult(true)
                                showAcceptDialog = true
                            }
                        )
                    }
                    Spacer(modifier = Modifier.Companion.width(200.dp))
                    Box(
                        modifier = Modifier.Companion.size(widthMd ),
                        contentAlignment = Alignment.Companion.Center
                    ) {

                        //Create animation for decline
                        RipplePulseEffect(
                            modifier = Modifier.Companion.fillMaxSize(),
                            color = Color.Companion.Red
                        )
                        CircleWithPipeImage(
                            imagePainter = painterResource(resource = Resources.Icon.callDecline),
                            color = Color.Companion.Red,
                            onClick = {
                                viewModel.stopAudio()
                                isAudioClicked = false
                                viewModel.setPhoneResult(false)
                                showAcceptDialog = true
                            }
                        )
                    }
                }
            }
        })

            //Dialog after accept
            if (showAcceptDialog) {
                CustomDialog(
                    onDismiss = { showAcceptDialog = false },
                    icon = Resources.Icon.callAccept,
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

        RegisterBackHandler(this)
        {
            navigator.popUntilRoot()
        }
    }
    }
}