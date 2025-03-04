package org.example.hit.heal.hitber.timeAndPlacee.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.hit.heal.core.presentation.Colors.primaryColor

data class DropDownItem(
    val text: String
)

@Composable
fun TimeAndPlaceQuestion(
    question: String,
    dropDownItems: List<DropDownItem>,
    modifier: Modifier = Modifier,
    onItemClick: (DropDownItem) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<DropDownItem?>(null) }
    var isClicked by remember { mutableStateOf(false) }

    val questionOffset by animateDpAsState(
        targetValue = if (expanded || selectedItem != null) -35.dp else 0.dp // שאלה עולה אם פתחנו את ה-Dropdown או אם נבחרה תשובה
    )

    val borderColor = if (isClicked) primaryColor else Color.Gray
    val textColor = if (isClicked) primaryColor else Color.Black

    Column(modifier = modifier.fillMaxWidth()) {

        // עטיפת השאלה באליפסה שחורה עם אפשרות ללחיצה
        Box(
            modifier = Modifier
                .fillMaxWidth() // שהמסגרת תהיה לכל רוחב המסך
                .height(80.dp) // גובה מוגדל למסגרת
                .background(Color.White, shape = RoundedCornerShape(20.dp))
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth() // שהמסגרת תהיה לכל רוחב המסך
                    .height(55.dp) // גובה מוגדל למסגרת
                    .border(2.dp, borderColor, shape = RoundedCornerShape(8)) // מסגרת שחורה מסביב
                    .padding(14.dp)
                    .clickable {
                        expanded = !expanded
                        isClicked = !isClicked // שינוי מצב הלחיצה
                    },
                horizontalArrangement = Arrangement.SpaceBetween, // זה ידאג לחלק את ה-Row
            ) {
                Box(modifier = Modifier.weight(1f)){
                Text(
                    text = question,
                    style = MaterialTheme.typography.h6.copy(fontSize = 12.sp),
                    color = textColor,
                    modifier = Modifier.offset(y = questionOffset) // השאלה תזוז למעלה כאן
                )
                selectedItem?.let {
                    Text(
                        text = it.text,
                        style = MaterialTheme.typography.body1.copy(fontSize = 12.sp))
                }}
                    Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Dropdown Icon")

            }
        }



        // dropdown menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // מגביל את הגובה של ה-dropdown menu ומוסיף גלילה אם נדרש
            Box(
                modifier = Modifier.heightIn(max = 300.dp) // גבול גובה
            ) {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()) // הוספת גלילה
                ) {
                    dropDownItems.forEach { item ->
                        DropdownMenuItem(onClick = {
                            selectedItem = item  // שמירת הפריט שנבחר
                            onItemClick(item)  // קריאה לפונקציה עם הפריט שנבחר
                            expanded = false
                            isClicked = false
                        }) {
                            Text(text = item.text, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        }
    }
}
