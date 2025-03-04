package org.example.hit.heal.hitber.timeAndPlacee.ui.components.questions

import androidx.compose.runtime.Composable
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.DropDownItem
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.TimeAndPlaceQuestion

@Composable
fun FifthQuestion() {
    val answers = listOf(
        DropDownItem("מרוקו"),
        DropDownItem("ארצות הברית"),
        DropDownItem("עיראק"),
        DropDownItem("לבנון"),
        DropDownItem("ישראל"),
        DropDownItem("תימן"),
        DropDownItem("רוסיה"),
        DropDownItem("אתיופיה")
    )

    TimeAndPlaceQuestion(
        question = "באיזו מדינה אתה גר?",
        dropDownItems = answers,
        onItemClick = {}
    )
}