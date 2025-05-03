package presentation.dialScreen

import BaseTabletScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import presentation.dialScreen.components.contactsDial
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.delete_number
import dmt_proms.pass.generated.resources.dial_keys
import dmt_proms.pass.generated.resources.white_phone
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.jetbrains.compose.resources.painterResource

class DialScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val dialViewModel: DialScreenViewModel = viewModel()

        val enteredNumber = dialViewModel.enteredNumber.collectAsState().value
        val isDialogVisible = dialViewModel.isDialogVisible.collectAsState().value

        BaseTabletScreen(
            title = "חייגן",
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    contactsDial.forEach { contact ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = contact.name + ":",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = contact.phoneNumber,
                                fontSize = 20.sp,
                                color = Color.DarkGray
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                    ) {
                        Button(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(60.dp)
                                .background(primaryColor, shape = RoundedCornerShape(10.dp)),
                            colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                            onClick = { dialViewModel.toggleDialog() }
                        ) {

                            Image(
                                painter = painterResource(Res.drawable.dial_keys),
                                contentDescription = "dial keys",
                                modifier = Modifier.size(30.dp)
                            )
                        }

                    }
                }

            }


        )


        if (isDialogVisible) {
            DialDialog(
                enteredNumber = enteredNumber,
                onNumberClicked = { dialViewModel.onNumberClicked(it) },
                onDial = { dialViewModel.dial() },
                onDeleteClicked = { dialViewModel.deleteLastDigit() }
            )
        }
    }

    @Composable
    fun DialDialog(
        enteredNumber: String,
        onNumberClicked: (String) -> Unit,
        onDial: () -> Unit,
        onDeleteClicked: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth().fillMaxHeight(0.5f)
                    .background(Color(0xFFD3D3D3)).align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, Color.Black, RoundedCornerShape(10.dp))
                        .padding(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.delete_number),
                            contentDescription = "delete number",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable { onDeleteClicked() }
                        )
                        Text(
                            text = enteredNumber,
                            fontSize = 18.sp,
                            color = Color.Gray,
                        )
                    }
                }


                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val numbers = listOf(
                        listOf("1", "2", "3"),
                        listOf("4", "5", "6"),
                        listOf("7", "8", "9"),
                        listOf("*", "0", "#")
                    )
                    numbers.forEach { row ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            row.forEach { number ->
                                Button(
                                    onClick = { onNumberClicked(number) },
                                    modifier = Modifier.width(70.dp).height(40.dp),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                                ) {
                                    Text(text = number, fontSize = 20.sp, color = Color.Black)
                                }
                            }
                        }
                    }
                }

                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = onDial,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .width(150.dp)
                            .padding(20.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "חיוג", fontSize = 18.sp, color = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Image(
                                painter = painterResource(Res.drawable.white_phone),
                                contentDescription = "phone",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

            }
        }
    }
}
