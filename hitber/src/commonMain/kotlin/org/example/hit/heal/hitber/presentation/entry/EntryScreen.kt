package org.example.hit.heal.hitber.presentation.entry

import TabletBaseScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.presentation.timeAndPlace.TimeAndPlace

class EntryScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        TabletBaseScreen(
            title = "HITBER",
            onNextClick = { navigator?.push(TimeAndPlace()) },
            question = 0,
            buttonText = "התחל",
            buttonColor = primaryColor,
            content = {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("הערה", color = Color.Red, fontSize = 40.sp)

                    Column(
                        modifier = Modifier.fillMaxWidth().padding(30.dp),
                        verticalArrangement = Arrangement.spacedBy(30.dp),
                        horizontalAlignment = Alignment.End,
                    ) {
                        Text("המשימה אורכת כ-20 דקות •", fontSize = 30.sp)
                        Text("יש להשלים את המשימה ברצף, ללא הפסקות•", fontSize = 30.sp)
                        Text("יש לקרוא את ההנחיות עד הסוף •", fontSize = 30.sp)
                        Text("יש להשלים את כל המטלות •", fontSize = 30.sp)
                        Text("לשמיעת ההוראות ניתן ללחוץ על הקשב •", fontSize = 30.sp)
                        Text("מומלץ לבצע את המשימות בחדר שקט. •", fontSize = 30.sp)
                    }

                    Text("ההוראות מנוסחות בלשון זכר אך מתייחסות לכל המינים •", fontSize = 25.sp)
                    Text("בהצלחה!", fontSize = 25.sp)
                }
            })
    }
}
