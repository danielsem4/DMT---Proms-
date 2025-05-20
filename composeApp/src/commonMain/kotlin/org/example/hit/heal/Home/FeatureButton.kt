package org.example.hit.heal.Home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


//@Composable
//fun FeatureButton(icon: ImageVector, label: String,onClick: () -> Unit) {
//    Card(
//        shape = RoundedCornerShape(16.dp),
//        backgroundColor = Color.White,
//        modifier = Modifier.padding(8.dp)
//            .padding(8.dp)
//            .size(100.dp)
//            .clickable(onClick = onClick)
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Icon(
//                imageVector = icon,
//                contentDescription = label,
//                modifier = Modifier.size(48.dp)
//            )
//            Spacer(modifier = Modifier.size(8.dp))
//            Text(text = label, fontSize = 12.sp)
//        }
//        }
//}

@Composable
fun FeatureButton(icon: ImageVector, label: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White,
        elevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .size(100.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Black
            )
        }
    }
}
//
//@Composable
//fun FeatureButton(
//    icon: ImageVector,
//    label: String,
//    onClick: () -> Unit // Add onClick parameter
//) {
//    Card(
//        shape = RoundedCornerShape(16.dp),
//        backgroundColor = Color.LightGray,
//        elevation = 4.dp,
//        modifier = Modifier
//            .padding(8.dp)
//            .size(100.dp)
//            .clickable(onClick = onClick) // Make the card clickable
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Icon(
//                imageVector = icon,
//                contentDescription = label,
//                tint = Color.Black,
//                modifier = Modifier.size(48.dp)
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = label,
//                fontSize = 12.sp,
//                color = Color.Black
//            )
//        }
//    }
//}
