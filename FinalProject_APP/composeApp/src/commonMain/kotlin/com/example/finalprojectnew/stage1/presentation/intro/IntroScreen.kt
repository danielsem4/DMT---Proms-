package com.example.finalprojectnew.presentation.stage1.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalprojectnew.presentation.stage1.components.GameButton
import com.example.finalprojectnew.presentation.theme.Colors

@Composable
fun IntroScreen(
    onStartGame: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(75.dp))

        Text(
            text = "שלב 1 - משימת המסוע",
            fontSize = 80.sp,
            fontWeight = FontWeight.Bold,
            color = Colors.PrimaryText,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .background(
                    color = Colors.CardBackground,
                    shape = RoundedCornerShape(16.dp)
                )
                .border(
                    width = 2.dp,
                    color = Colors.SecondaryText,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = ".במשימה זו תראו מצרכים עוברים על מסוע\n\n" +
                            "יש לבחור רק את המצרכים שמתאימים למנה שבחרת, לפי הרשימה שמופיעה בצד המסך",
                    fontSize = 60.sp,
                    color = Colors.SecondaryText,
                    textAlign = TextAlign.Center,
                    lineHeight = 55.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        Spacer(modifier = Modifier.height(56.dp))

        GameButton(
            text = "לחצו להתחלת המשחק",
            onClick = onStartGame,
            modifier = Modifier
                .height(100.dp)
                .padding(horizontal = 48.dp),
            textSize = 42
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}
