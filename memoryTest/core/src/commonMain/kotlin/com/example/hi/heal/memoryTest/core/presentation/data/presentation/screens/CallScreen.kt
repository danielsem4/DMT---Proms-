package com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.hi.heal.memoryTest.core.presentation.data.backgroundColor

import com.example.hi.heal.memoryTest.core.presentation.data.presentation.components.CircleWithPipeImage
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.components.CustomDialog
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.components.effects.RipplePulseEffect
import com.example.hi.heal.memoryTest.core.presentation.data.primaryColor
import dmt_proms.memorytest.core.generated.resources.Res
import dmt_proms.memorytest.core.generated.resources.call_accept
import dmt_proms.memorytest.core.generated.resources.call_remove
import dmt_proms.memorytest.core.generated.resources.coffee
import org.jetbrains.compose.resources.painterResource

class CallScreen( val txtMemoryPage: Int = 1)  : Screen {
    @Composable

    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        var showAcceptDialog by remember { mutableStateOf(false) }

        BaseTabletScreen(title = "שיחה נכנסת", page = txtMemoryPage, totalPages = 6) {

            Column(
                modifier = Modifier
                    .background(color = backgroundColor)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "שיחה נכנסת",
                    fontSize = 32.sp,
                    color = primaryColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier

                        .wrapContentHeight()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "בית רבקה",
                    fontSize = 24.sp,
                    color = primaryColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier

                        .wrapContentHeight()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "052-538-1648",
                    fontSize = 24.sp,
                    color = primaryColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier

                        .wrapContentHeight()
                        .padding(24.dp),
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        RipplePulseEffect(
                            modifier = Modifier.fillMaxSize(),
                            color = primaryColor
                        )

                        CircleWithPipeImage(
                            imagePainter = painterResource(Res.drawable.call_accept),
                            color = primaryColor,
                            onClick = { showAcceptDialog = true }
                        )
                    }
                    Spacer(modifier = Modifier.width(200.dp))


                    Box(
                        modifier = Modifier.size(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        RipplePulseEffect(
                            modifier = Modifier.fillMaxSize(),
                            color = Color.Red
                        )

                        CircleWithPipeImage(
                            imagePainter = painterResource(Res.drawable.call_remove),
                            color = Color.Red
                        )
                    }
                }
                }

            }
        var showDialog by rememberSaveable { mutableStateOf(true) }
        if (showAcceptDialog) {
            CustomDialog(
                onDismiss = { showDialog = false },
                icon = {
                    Icon(Icons.Default.ThumbUp, contentDescription = null, tint = Color.White, modifier = Modifier.size(40.dp))
                },
                title = "תודה",
                description = "המשימה הסתיימה, תודה רבה על שיתוף הפעולה",
                buttons = listOf("הבא" to { navigator.push(RoomsScreens(txtMemoryPage = 2)) })
            )
        }
        }

    }
