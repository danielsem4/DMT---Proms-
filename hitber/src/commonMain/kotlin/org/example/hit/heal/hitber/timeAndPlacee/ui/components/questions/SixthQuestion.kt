package org.example.hit.heal.hitber.timeAndPlacee.ui.components.questions

import androidx.compose.runtime.Composable
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.DropDownItem
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.TimeAndPlaceQuestion

@Composable
fun SixthQuestion() {
    val answers = listOf(
        DropDownItem("מרכז"),
        DropDownItem("צפון"),
        DropDownItem("דרום"),
        DropDownItem("הגליל המערבי"),
        DropDownItem("צפון השרון"),
        DropDownItem("מישור החוף")
    )

    TimeAndPlaceQuestion(
        question = "באיזה איזור בישראל אתה נמצא?",
        dropDownItems = answers,
        onItemClick = {}
    )
}