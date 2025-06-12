package com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens

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
import androidx.compose.runtime.remember
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
import com.example.hi.heal.memoryTest.core.presentation.data.backgroundColor
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.ViewModelMemoryTest.ViewModelMemoryTest
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.components.BulletPointText
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.RoomScreen.RoomsScreens
import com.example.hi.heal.memoryTest.core.presentation.data.primaryColor
import org.koin.compose.viewmodel.koinViewModel


class MemoryScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: ViewModelMemoryTest = viewModel()
        BaseTabletScreen(title = "Memory", page = 1, totalPages =6 ){

        Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = backgroundColor),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "הוראות",
                fontSize = 32.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            BulletPointText(text = "המשימה אורכת כ-20 דקות ")
            BulletPointText(text = "יש להשלים את המשימה ברצף, ללא הפסקות ")
            BulletPointText(text = "יש לקרוא את ההנחיות עד הסוף ")
            BulletPointText(text = "יש להשלים את כל המטלות ")
            BulletPointText(text = "לשמיעת ההוראות ניתן ללחוץ על הקשב ")
            BulletPointText(text = " מומלץ לבצע את המשימה בחדר שקט ")
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "בהצלחה !",
                fontSize = 32.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = " לתחילת המשימה לחץ על התחל",
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
                    Text(text = "התחל", fontSize = 24.sp,  color = Color.White)
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}