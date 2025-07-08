package com.example.new_memory_test.presentation.screens.InformScheduleScreen.screen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.new_memory_test.presentation.components.dialogs.CustomDialog
import com.example.new_memory_test.presentation.screens.BaseTabletScreen
import com.example.new_memory_test.presentation.screens.InformScheduleScreen.components.ActivityItem
import com.example.new_memory_test.presentation.screens.ScheduleScreen.screen.ScheduleScreen
import com.example.new_memory_test.primaryColor
import org.example.hit.heal.core.presentation.Resources
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class InformScheduleScreen(val pageNumber: Int) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var showAcceptDialog by remember { mutableStateOf(false) }
        val viewModel: ViewModelMemoryTest = viewModel()

        showAcceptDialog = true
        BaseTabletScreen(title = stringResource(Resources.String.build_schedule), page = pageNumber, totalPages = 6) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text =  stringResource(Resources.String.memory_instruction),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Белая карточка с активностями
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        ActivityItem(
                            stringResource(Resources.String.dumbbell_circle_text),
                            icon = painterResource(Resources.Icon.dumbbellScheduleIcon)
                        )
                        ActivityItem(
                            stringResource(Resources.String.stethoscope_circle_text),
                            icon = painterResource(Resources.Icon.stethascopeScheduleIcon)
                        )
                        ActivityItem(
                            stringResource(Resources.String.book_circle_text),
                            icon = painterResource(Resources.Icon.bookScheduleIcon)
                        )
                        ActivityItem(
                            stringResource(Resources.String.coffee_circle_text),
                            icon = painterResource(Resources.Icon.coffeeScheduleIcon)
                        )
                        ActivityItem(
                            stringResource(Resources.String.lecturer_circle_text),
                            icon = painterResource(Resources.Icon.teachScheduleIcon)
                        )
                        ActivityItem(
                            stringResource(Resources.String.move_circle_text),
                            icon = painterResource(Resources.Icon.moveScheduleIcon)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        viewModel.setPage(viewModel.txtMemoryPage + 1)
                        navigator.push(ScheduleScreen(pageNumber = 5))
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = primaryColor
                    ),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .defaultMinSize(minWidth = 100.dp)
                        .height(50.dp)
                        .height(56.dp)
                ) {
                    Text(stringResource(Resources.String.start_button), color = Color.White, fontSize = 18.sp)
                }
            }

            if (showAcceptDialog) {
                CustomDialog(
                    onDismiss = { showAcceptDialog = true },
                    icon = {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    },
                    title =stringResource(Resources.String.whatWillYouDo),
                    description = stringResource(Resources.String.memory_instruction),
                    buttons = listOf(
                        stringResource(Resources.String.remember_all) to { viewModel.selectedReminderOption.value = "אזכור הכל"
                            showAcceptDialog = false },
                        stringResource(Resources.String.talk_to_family) to { viewModel.selectedReminderOption.value = "אדבר עם המשפחה"
                            showAcceptDialog = false },
                        stringResource(Resources.String.use_diary) to { viewModel.selectedReminderOption.value = "אשתמש ביומן"
                            showAcceptDialog = false }
                    )
                )
            }
        }
    }
}