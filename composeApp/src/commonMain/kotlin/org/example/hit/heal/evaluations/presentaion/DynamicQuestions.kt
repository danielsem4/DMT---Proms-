package org.example.hit.heal.evaluations.presentaion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.hit.heal.core.presentation.BaseScreen
import org.example.hit.heal.core.presentation.Colors.primaryColor

@Composable
fun DynamicScrollScreen(
    content: @Composable() (ColumnScope.() -> Unit),
    onPrev: (() -> Unit)? = null,
    onNext: (() -> Unit)? = null
) {
    val name = "Report"
    val question = "Question"
    BaseScreen(title = "Evaluation", onPrevClick = onPrev, onNextClick = onNext) {
        Text(
            text = question,
            fontSize = 32.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp),
            fontWeight = FontWeight.Bold
        )

        // Outer Box (Container)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(16.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        primaryColor,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name,
                    fontSize = 24.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Scrollable Content
            Column(
                modifier = Modifier
                    .align(Alignment.Start)
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
fun SampleDyn() {
    DynamicScrollScreen(ReportExample())
}