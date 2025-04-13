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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.example.hi.heal.memoryTest.core.presentation.data.primaryColor



class MemoryScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val txtMemoryPage =1
        BaseTabletScreen(title = "Memory", page = txtMemoryPage, totalPages =6 ){
        }
        Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = backgroundColor),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = primaryColor)

                ) {
                    Text(
                        text = "Memory",
                        fontSize = 32.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text =  "$txtMemoryPage/6",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterEnd).padding(16.dp),
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "הערה",
                    fontSize = 32.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = " המשימה אורכת כ-20 דקות",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(20.dp))

                val items = List(4) { " המשימה אורכת כ-20 דקות" }
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)

                ) {
                    items.forEach {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = it,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                textAlign = TextAlign.Right,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "•",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "ההוראות מנוסחות בלשון זכר אך מתייחסות לכל המינים",
                    fontSize = 24.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 24.dp)
                )

                Button(
                    onClick = {
                        navigator.push(RoomsScreens(txtMemoryPage = 1))
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .height(50.dp)
                ) {
                    Text(text = "התחל", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
}