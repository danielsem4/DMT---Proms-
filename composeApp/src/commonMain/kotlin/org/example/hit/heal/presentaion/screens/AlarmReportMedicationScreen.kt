package org.example.hit.heal.presentaion.screens

import MedicationScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.hit.heal.presentaion.components.RoundedButton
import org.example.hit.heal.presentaion.primaryColor


class AlarmReportMedicationScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        BaseScreen(title = "Medications") {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFCDEAE0)),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {navigator.push(MedicationScreen(isReport = true))},
                    colors = ButtonDefaults.buttonColors(Color.White),
                    shape = RoundedCornerShape(20),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(16.dp)
                        .height(50.dp),


                ){
                    Icon(Icons.Default.Star, contentDescription = "Add")
                    Text(text = "Report medication ", fontSize = 18.sp,  modifier = Modifier.padding(horizontal = 8.dp))

                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {navigator.push(MedicationScreen(isReport = false))},
                    colors = ButtonDefaults.buttonColors(Color.White),
                    shape = RoundedCornerShape(20),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(16.dp)
                        .height(50.dp),


                    ){
                    Icon(Icons.Default.Star, contentDescription = "Add")
                    Text(text = "Set medication alarm", fontSize = 18.sp,  modifier = Modifier.padding(horizontal = 8.dp))

                }


            }
        }
    }
}