package com.example.new_memory_test.presentation.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.backgroundColor
import org.example.hit.heal.core.presentation.primaryColor


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
                .padding(horizontal = paddingMd, vertical = paddingMd),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontSize = LARGE,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
                    .padding(paddingMd),
                textAlign = TextAlign.Center
            )
            Text(
                text = "$page/$totalPages",
                fontSize = LARGE,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(paddingMd)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingMd),
            content = content
        )
    }
}

