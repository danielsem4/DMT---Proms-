package org.example.hit.heal.hitber.entry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.hit.heal.core.presentation.BaseScreen
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.timeAndPlacee.ui.TimeAndPlace

class EntryScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        BaseScreen(title = "HITBER", onPrevClick = null, onNextClick = null, content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("הערה", color = Color.Red, fontSize = 24.sp)

                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text("המשימה אורכת כ-20 דקות •", fontSize = 20.sp)
                    Text("יש להשלים את המשימה ברצף, ללא הפסקות•", fontSize = 20.sp)
                    Text("יש לקרוא את ההנחיות עד הסוף •", fontSize = 20.sp)
                    Text("יש להשלים את כל המטלות •", fontSize = 20.sp)
                    Text("לשמיעת ההוראות ניתן ללחוץ על הקשב •", fontSize = 20.sp)
                    Text("מומלץ לבצע את המשימות בחדר שקט. •", fontSize = 20.sp)
                }

                Text("ההוראות מנוסחות בלשון זכר אך מתייחסות לכל המינים •", fontSize = 15.sp)
                Text("בהצלחה!", fontSize = 15.sp)

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp),
                        onClick = { navigator?.push(TimeAndPlace()) },
                        colors = ButtonDefaults.buttonColors(primaryColor),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text("התחל", color = Color.Black, fontSize = 15.sp)
                    }
                }
            }

        })
    }
}
