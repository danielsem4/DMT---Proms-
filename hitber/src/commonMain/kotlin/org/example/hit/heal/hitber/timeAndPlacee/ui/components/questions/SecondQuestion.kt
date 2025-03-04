package org.example.hit.heal.hitber.timeAndPlacee.ui.components.questions

import androidx.compose.runtime.Composable
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.DropDownItem
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.TimeAndPlaceQuestion

@Composable
fun SecondQuestion() {
    val answers = listOf(
        DropDownItem("ינואר"),
        DropDownItem("פברואר"),
        DropDownItem("מרץ"),
        DropDownItem("אפריל"),
        DropDownItem("מאי"),
        DropDownItem("יוני"),
        DropDownItem("יולי"),
        DropDownItem("אוגוסט"),
        DropDownItem("ספטמבר"),
        DropDownItem("אוקטובר"),
        DropDownItem("נובמבר"),
        DropDownItem("דצמבר"),
    )

    TimeAndPlaceQuestion(
        question = "באיזה חודש אנחנו נמצאים?",
        dropDownItems = answers,
        onItemClick = {}
    )
}