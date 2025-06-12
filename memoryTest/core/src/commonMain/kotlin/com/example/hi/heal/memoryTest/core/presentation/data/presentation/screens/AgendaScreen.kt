package com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.ViewModelMemoryTest.ViewModelMemoryTest
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.components.dialogs.CustomDialog
import com.example.hi.heal.memoryTest.core.presentation.data.primaryColor
import dmt_proms.memorytest.core.generated.resources.Res
import dmt_proms.memorytest.core.generated.resources.book_icon
import dmt_proms.memorytest.core.generated.resources.coffee_icon
import dmt_proms.memorytest.core.generated.resources.lecturer_icon
import dmt_proms.memorytest.core.generated.resources.move_icon
import dmt_proms.memorytest.core.generated.resources.stethoscope_icon

import org.jetbrains.compose.resources.painterResource


class AgendaScreen(val pageNumber: Int) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var showAcceptDialog by remember { mutableStateOf(false) }
        val viewModel: ViewModelMemoryTest = viewModel()

        showAcceptDialog = true
        BaseTabletScreen(title = "בניית סדר יום", page = pageNumber, totalPages = 6) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "לפניך מספר פעילויות שעליך לעשות לשבץ במערכת שעות. להתחלת המשימה לחץ על המשך",
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
                            text = "חוג התעמלות במתנ\"ס ביום ראשון בשעה 11:00 במשך שעה."
                            ,icon = painterResource(Res.drawable.stethoscope_icon )
                        )
                        ActivityItem(" אני הולך לעיניים ביום שישי בין השעות 09:00 - 12:00 במשך שעה.",icon = painterResource(Res.drawable.stethoscope_icon ))
                        ActivityItem("לקחת את סבתא לבית ספר לסיפור חיים ביום שני בשעה 16:00. ", icon = painterResource(Res.drawable.book_icon))
                        ActivityItem("שיחה עם מדריך בבית ספר לסיפור חיים ביום חמישי בשעה 15:00 במשך שעה. ",icon = painterResource(Res.drawable.coffee_icon ))
                        ActivityItem("עזרה למורה לכתיבה ביום ראשון בשעה 15:00 במשך שעה. ", icon = painterResource(Res.drawable.lecturer_icon))
                        ActivityItem("אני מתפלל בבית כנסת ביום שישי בבוקר במשך שעה עשר דקות. ", icon = painterResource(Res.drawable.move_icon))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.setPage(viewModel.txtMemoryPage + 1)
                        navigator.push(AgendaContinuationScreen(pageNumber = 5))},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = primaryColor
                    ),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .defaultMinSize(minWidth = 100.dp)
                        .height(50.dp)
                        .height(56.dp)
                ) {


                    Text("התחל", color = Color.White, fontSize = 18.sp)
                }
            }


            if (showAcceptDialog) {
                CustomDialog(
                    onDismiss = { showAcceptDialog = false },
                    icon = {
                        Icon(Icons.Default.Share, contentDescription = null, tint = Color.White, modifier = Modifier.size(40.dp))
                    },
                    title = "מה תעשה ?",
                    description = "לפניך מערכת שעות של פעילויות. מה עליך לעשות כדי לזכור את כל הפעילויות המפורטות?",
                    buttons = listOf(
                        "אזכור הכל" to { showAcceptDialog = false },
                        "אדבר עם המשפחה" to { showAcceptDialog = false },
                        "אשתמש ביומן" to { showAcceptDialog = false }
                    )
                )
            }
        }
    }

}

@Composable
fun ActivityItem(text: String, icon: Painter) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Right,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
        )
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(45.dp)
        )
    }
}
