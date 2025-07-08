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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.new_memory_test.backgroundColor
import com.example.new_memory_test.presentation.ViewModel.ViewModelMemoryTest
import com.example.new_memory_test.presentation.components.BulletPointText
import com.example.new_memory_test.presentation.screens.BaseTabletScreen
import com.example.new_memory_test.presentation.screens.RoomScreen.components.enum_room.Room
import com.example.new_memory_test.presentation.screens.RoomScreen.screen.RoomsScreens
import com.example.new_memory_test.primaryColor
import org.example.hit.heal.core.presentation.Resources
import org.jetbrains.compose.resources.stringResource

class MemoryScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: ViewModelMemoryTest = viewModel()
        BaseTabletScreen(title = stringResource(Resources.String.memory_title), page = 1, totalPages =6 ){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = backgroundColor),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = stringResource(Resources.String.instructions),
                    fontSize = 32.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                BulletPointText(text = stringResource(Resources.String.task_duration ))
                BulletPointText(text =  stringResource(Resources.String.task_continuity ))
                BulletPointText(text = stringResource(Resources.String.read_instructions ))
                BulletPointText(text = stringResource(Resources.String.complete_all_tasks ))
                BulletPointText(text = stringResource(Resources.String.listen_instruction_tip ))
                BulletPointText(text = stringResource(Resources.String.quiet_room_tip ))
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = stringResource(Resources.String.good_luck ),
                    fontSize = 32.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = stringResource(Resources.String.press_start_to_begin ),
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        viewModel.setPage(viewModel.txtMemoryPage + 1)
                        navigator.push(RoomsScreens(pageNumber = 2))
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .height(50.dp)
                ) {
                    Text( stringResource(Resources.String.next), fontSize = 24.sp,  color = Color.White)
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}