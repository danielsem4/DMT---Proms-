package org.example.hit.heal.Home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

//@Composable
//fun MessagesSection() {
//    Card(
//        shape = RoundedCornerShape(16.dp),
//        backgroundColor = Color.White,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(bottom = 16.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text("Messages", fontSize = 18.sp, color = Color.Black)
//            Spacer(modifier = Modifier.height(8.dp))
//            Text("• Don't forget to take your test.", fontSize = 14.sp)
//            Text("• Take 2 pills at 12:00", fontSize = 14.sp)
//        }
//    }
//}
@Composable
fun MessagesSection(content: @Composable () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}