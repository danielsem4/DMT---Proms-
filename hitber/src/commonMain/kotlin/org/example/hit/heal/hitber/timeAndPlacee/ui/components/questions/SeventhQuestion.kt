package org.example.hit.heal.hitber.timeAndPlacee.ui.components.questions

import androidx.compose.runtime.Composable
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.DropDownItem
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.TimeAndPlaceQuestion

@Composable
fun SeventhQuestion() {
    val answers = listOf(
        DropDownItem("אני לא יודע"),
        DropDownItem("כי אני בודק את תפקודיהחשיבה שלי"),
        DropDownItem("אמרו לי להגיע"),
        DropDownItem("זו פעילות שגרתית עבורי")
    )

    TimeAndPlaceQuestion(
        question = "למה אתה עונה לשאלון הזה?",
        dropDownItems = answers,
        onItemClick = {}
    )
}