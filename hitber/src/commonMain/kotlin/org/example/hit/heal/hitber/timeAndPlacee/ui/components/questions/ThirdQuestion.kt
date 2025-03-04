package org.example.hit.heal.hitber.timeAndPlacee.ui.components.questions

import androidx.compose.runtime.Composable
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.DropDownItem
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.TimeAndPlaceQuestion

@Composable
fun ThirdQuestion() {
    val answers = listOf(
        DropDownItem("1994"),
        DropDownItem("2024"),
        DropDownItem("1964"),
        DropDownItem("2025"),
        DropDownItem("2000"),
        DropDownItem("2023"),
        DropDownItem("1924"),
        DropDownItem("1948")
    )

    TimeAndPlaceQuestion(
        question = "באיזו שנה אנחנו נמצאים?",
        dropDownItems = answers,
        onItemClick = {}
    )
}