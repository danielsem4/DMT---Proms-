package com.example.new_memory_test.presentation.screens.MemoryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

import com.example.new_memory_test.presentation.ViewModel.ViewModelMemoryTest
import com.example.new_memory_test.presentation.components.BulletPointText
import com.example.new_memory_test.presentation.screens.BaseTabletScreen
import com.example.new_memory_test.presentation.screens.RoomInsructionsScreen.RoomInstructionsScreen
import com.example.new_memory_test.presentation.screens.RoomScreen.components.enum_room.Room
import com.example.new_memory_test.presentation.screens.RoomScreen.screen.RoomsScreens
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM_LARGE
import org.example.hit.heal.core.presentation.FontSize.EXTRA_REGULAR
import org.example.hit.heal.core.presentation.FontSize.LARGE


import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Sizes.buttonHeightMd
import org.example.hit.heal.core.presentation.Sizes.heightSm
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.Sizes.spacingXl
import org.example.hit.heal.core.presentation.backgroundColor
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class MemoryScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: ViewModelMemoryTest = koinViewModel()
        viewModel.reset()

        //create at evaluetion memory
        LaunchedEffect(Unit){
            viewModel.loadEvaluation("Memory")
        }

        //Save in one side and don't depend on language
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr){
        BaseTabletScreen(title = stringResource(Resources.String.memory_title), page = 1, totalPages =6 ){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = backgroundColor),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(spacingXl))
                Text(
                    text = stringResource(Resources.String.instructions),
                    fontSize =LARGE,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(spacingMd))
                BulletPointText(text = stringResource(Resources.String.task_duration ))
                BulletPointText(text =  stringResource(Resources.String.task_continuity ))
                BulletPointText(text = stringResource(Resources.String.read_instructions ))
                BulletPointText(text = stringResource(Resources.String.complete_all_tasks ))
                BulletPointText(text = stringResource(Resources.String.quiet_room_tip ))
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = stringResource(Resources.String.good_luck ),
                    fontSize =LARGE,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(spacingXl))

                Text(
                    text = stringResource(Resources.String.press_start_to_begin ),
                    fontSize = EXTRA_REGULAR,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(spacingMd))

                Button(
                    onClick = {
                        viewModel.setPage(viewModel.txtMemoryPage + 1)
                        navigator.push(RoomInstructionsScreen(pageNumber = 1))
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                    shape = RoundedCornerShape(30),
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .height(buttonHeightMd)
                ) {
                    Text( stringResource(Resources.String.start), fontSize = EXTRA_MEDIUM_LARGE,  color = Color.White)
                }
                Spacer(modifier = Modifier.height(heightSm))
            }
            }
        }
        RegisterBackHandler(this)
        {
            navigator.popUntilRoot()
        }
    }

}