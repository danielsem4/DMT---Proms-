package presentation.result

import BaseTabletScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.hit.heal.core.presentation.Colors.primaryColor

class ResultScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        BaseTabletScreen(
            title = "סיום",
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "כותרת התוצאות",
                        fontSize = 25.sp,
                        fontWeight = Bold,
                        color = primaryColor,
                        modifier = Modifier.padding(10.dp)
                    )

                    Row(
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                                .background(Color.White)
                                .border(2.dp, Color.LightGray)
                                .padding(16.dp)
                        ) {
                            Column {
                                Text("כותרת המשימה הראשונה", fontSize = 20.sp, fontWeight = Bold, color = primaryColor)
                                Text("מצא את האפילקציה הנכונה: הנחיה מילולית כללית", fontSize = 20.sp, color = primaryColor)
                                Text("מצא את איש הקשר הנכון: ביצוע עצמאי", fontSize = 20.sp, color = primaryColor)
                                Text("התקשר לאיש הקשר הנכון: הנחיה מילוית כללית:", fontSize = 20.sp, color = primaryColor)
                                Text("לסיכום:", fontSize = 20.sp, color = primaryColor)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                                .background(Color.White)
                                .border(2.dp, Color.LightGray)
                                .padding(16.dp)
                        ) {
                            Column {
                                Text("כותרת המשימה השנייה", fontSize = 20.sp, fontWeight = Bold, color = primaryColor)
                                Text("מצא את לחצן החיוג: ביצוע עצמאי", fontSize = 20.sp, color = primaryColor)
                                Text("כתוב את המספר הנכון: הנחיה מילולית ישירה", fontSize = 20.sp, color = primaryColor)
                                Text("חייג למשימת רופא השיניים: ביצוע עצמאי", fontSize = 20.sp, color = primaryColor)
                                Text("לסיכום:", fontSize = 20.sp, color = primaryColor)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f)) 

                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                        modifier = Modifier
                            .width(200.dp)
                            .padding(bottom = 10.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("סיום", fontSize = 20.sp, fontWeight = Bold, color = Color.White)
                    }
                }
            }
        )
    }
}
