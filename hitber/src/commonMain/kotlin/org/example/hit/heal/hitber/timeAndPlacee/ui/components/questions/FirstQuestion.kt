package org.example.hit.heal.hitber.timeAndPlacee.ui.components.questions

import androidx.compose.runtime.Composable
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.DropDownItem
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.TimeAndPlaceQuestion

@Composable
fun FirstQuestion() {
    val answers = listOf(
        DropDownItem("ראשון"),
        DropDownItem("שני"),
        DropDownItem("שלישי"),
        DropDownItem("רביעי"),
        DropDownItem("חמישי"),
        DropDownItem("שישי"),
        DropDownItem("שבת")
    )

    TimeAndPlaceQuestion(
        question = "איזה יום היום?",
        dropDownItems = answers,
        onItemClick = {}
    )
}