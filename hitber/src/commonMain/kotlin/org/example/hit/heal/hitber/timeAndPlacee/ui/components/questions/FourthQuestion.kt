package org.example.hit.heal.hitber.timeAndPlacee.ui.components.questions

import androidx.compose.runtime.Composable
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.DropDownItem
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.TimeAndPlaceQuestion

@Composable
fun ForthQuestion() {
    val answers = listOf(
        DropDownItem("גני תקווה"),
        DropDownItem("חיפה"),
        DropDownItem("תל אביב"),
        DropDownItem("פתח תקווה"),
        DropDownItem("ראשון לציון"),
        DropDownItem("כפר סבא"),
        DropDownItem("ראש העין"),
        DropDownItem("בני ברק"),
        DropDownItem("ירושלים"),
        DropDownItem("באר שבע")
    )

    TimeAndPlaceQuestion(
        question = "באיזו עיר אתה נמצא כרגע?",
        dropDownItems = answers,
        onItemClick = {}
    )
}