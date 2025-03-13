//package org.example.hit.heal.hitber.timeAndPlacee.ui.components
//
//import androidx.compose.animation.core.animateDpAsState
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.heightIn
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.DropdownMenu
//import androidx.compose.material.DropdownMenuItem
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import dmt_proms.hitber.generated.resources.Res
//import dmt_proms.hitber.generated.resources.drop_down
//import org.example.hit.heal.core.presentation.Colors.primaryColor
//import org.example.hit.heal.hitber.ActivityViewModel
//import org.jetbrains.compose.resources.painterResource
// data class DropDownItem(val text: String)
//
//@Composable
//fun TimeAndPlaceQuestion(
//    question: String,
//    dropDownItems: List<DropDownItem>,
//    viewModel: ActivityViewModel,
//    modifier: Modifier = Modifier,
//    onItemClick: (DropDownItem) -> Unit
//) {
//    var expanded by remember { mutableStateOf(false) }
//
//    val isFocused = question == viewModel.focusedQuestion.value
//    val borderColor = if (isFocused) primaryColor else Color.Gray
//    val textColor = if (isFocused) primaryColor else Color.Black
//    val selectedItem = viewModel.answersMap.value[question]
//
//    val questionOffset by animateDpAsState(
//        targetValue = if (expanded || selectedItem != null) -30.dp else 0.dp
//    )
//
//    Column(modifier = modifier.fillMaxWidth()) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(80.dp)
//                .background(Color.White, shape = RoundedCornerShape(20.dp))
//                .padding(16.dp),
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(45.dp)
//                    .border(1.dp, borderColor, shape = RoundedCornerShape(8))
//                    .padding(10.dp)
//                    .clickable {
//                        expanded = !expanded
//                        viewModel.setFocusedQuestion(question)
//                    },
//                horizontalArrangement = Arrangement.SpaceBetween,
//            ) {
//                Box(modifier = Modifier.weight(1f)) {
//                    Text(
//                        text = question,
//                        style = MaterialTheme.typography.h6.copy(fontSize = 12.sp),
//                        color = textColor,
//                        modifier = Modifier.offset(y = questionOffset)
//                    )
//                    selectedItem?.let {
//                        Text(
//                            text = it.text,
//                            style = MaterialTheme.typography.body1.copy(fontSize = 12.sp)
//                        )
//                    }
//                }
//                Image(
//                    painter = painterResource(Res.drawable.drop_down),
//                    contentDescription = "drop down icon",
//                    modifier = Modifier.size(20.dp)
//                )
//            }
//        }
//
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
//            modifier = Modifier
//                .fillMaxWidth()
//        ) {
//            Box(
//                modifier = Modifier.heightIn(max = 300.dp)
//            ) {
//                Column(
//                    modifier = Modifier.verticalScroll(rememberScrollState())
//                ) {
//                    dropDownItems.forEach { item ->
//                        DropdownMenuItem(onClick = {
//                            viewModel.setAnswers(question, item)
//                            onItemClick(item)
//                            expanded = false
//                        }) {
//                            Text(text = item.text, modifier = Modifier.fillMaxWidth())
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.drop_down
import dmt_proms.hitber.generated.resources.drop_up
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.jetbrains.compose.resources.painterResource

data class DropDownItem(val text: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeAndPlaceQuestion(
    question: String,
    dropDownItems: List<DropDownItem>,
    viewModel: ActivityViewModel,
    modifier: Modifier = Modifier,
    onItemClick: (DropDownItem) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedItem = viewModel.answersTimeAndPlace.value[question]

    Box(  modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .background(Color.White, shape = RoundedCornerShape(20.dp))
                .padding(16.dp)){
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = primaryColor,focusedLabelColor = primaryColor),
            value = selectedItem?.text ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(question) },
            trailingIcon = {
                Image(
                    painter = painterResource(
                        if (expanded) Res.drawable.drop_up else Res.drawable.drop_down
                    ),
                    contentDescription = "Drop down drop up Icon",
                    modifier = Modifier.size(20.dp)
                )
            },

            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            dropDownItems.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        viewModel.setAnswersTimeAndPlace(question, item)
                        onItemClick(item)
                        expanded = false
                    }
                ) {
                    Text(text = item.text)
                }
            }
        }}
    }
}

