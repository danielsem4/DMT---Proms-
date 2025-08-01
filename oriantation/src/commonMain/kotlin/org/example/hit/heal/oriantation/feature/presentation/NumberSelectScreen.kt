package org.example.hit.heal.oriantation.feature.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.DrawerDefaults.shape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.Black
import org.example.hit.heal.core.presentation.Resources.String.secQuestionInstruction
import org.example.hit.heal.core.presentation.Resources.String.secQuestionNumber
import org.example.hit.heal.core.presentation.Resources.String.secQuestionTitle
import org.example.hit.heal.core.presentation.TabletBaseScreen
import org.example.hit.heal.core.presentation.backgroundColor
import org.example.hit.heal.oriantation.data.model.OrientationTestViewModel
import org.jetbrains.compose.resources.stringResource

class NumberSelectScreen(
    private val viewModel: OrientationTestViewModel
) : Screen {    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var expanded by remember { mutableStateOf(false) }
        var selectedNumber by remember { mutableStateOf(0) }
        val numbers = (0..20).toList()

        TabletBaseScreen(
            title = (stringResource(secQuestionTitle)),
            question = 2,
            onNextClick = { navigator?.push(SeasonsSelectScreen(viewModel)) },
            content = {
                Spacer(modifier = Modifier.height(32.dp))

                // Instruction Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = 4.dp,
                    backgroundColor = Color.White,
                    border = BorderStroke(1.dp, Color(0xFF4EC3AF))
                ) {
                    Box(
                        modifier = Modifier
                            .padding(16.dp),
//                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(secQuestionInstruction),
                            color = Color(0xFF4EC3AF),
                            fontSize = 22.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Number Selector
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .background(Black)

                ) {
                    OutlinedButton(

                        onClick = { expanded = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),

                        border = BorderStroke(1.dp, Color(0xFF4EC3AF)),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = backgroundColor)
                    ) {
                        Text(
                            text = selectedNumber.toString(),
                            color = Color(0xFF4EC3AF),
                            fontSize = 22.sp
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        modifier = Modifier.
                            fillMaxWidth().
                            padding(horizontal = 24.dp).
                            fillMaxHeight(0.5f),
                        onDismissRequest = { expanded = false }
                    ) {
                        numbers.forEach { number ->
                            DropdownMenuItem(onClick = {
                                selectedNumber = number
                                viewModel.updateNumber(number)
                                expanded = false
                            }) {
                                Text(text = number.toString()
                                ,modifier = Modifier.
                                    fillMaxWidth()
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(150.dp))

                // Selected Number Card
                Card(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 24.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = 4.dp,
                    backgroundColor = Color.White,
                    border = BorderStroke(1.dp, Color(0xFF4EC3AF))
                ) {
                    Box(
                        modifier = Modifier
                            .padding(16.dp),
//                            .fillMaxWidth(),
//                            .height(20.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
//

                            text = "${stringResource(secQuestionNumber)}, $selectedNumber",
                            color = Color(0xFF4EC3AF),
                            fontSize = 22.sp
                        )
                    }
                }
            }
        )
    RegisterBackHandler(this)
    {
        navigator?.popUntilRoot()
    }
    }
}