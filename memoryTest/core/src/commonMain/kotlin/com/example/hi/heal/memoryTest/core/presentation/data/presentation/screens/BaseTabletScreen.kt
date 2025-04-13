package com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hi.heal.memoryTest.core.presentation.data.backgroundColor
import com.example.hi.heal.memoryTest.core.presentation.data.primaryColor

@Composable
fun BaseTabletScreen(
    title: String,
    page: Int,
    totalPages: Int,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(primaryColor)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "$page/$totalPages",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(16.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            content = content
        )
    }
}

