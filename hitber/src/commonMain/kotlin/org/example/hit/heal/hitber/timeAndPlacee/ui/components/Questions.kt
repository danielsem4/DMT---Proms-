package org.example.hit.heal.hitber.timeAndPlacee.ui.components

import DropDownItem
import TimeAndPlaceQuestion
import androidx.compose.runtime.Composable
import org.example.hit.heal.hitber.ActivityViewModel

@Composable
fun Questions(viewModel: ActivityViewModel) {

    val questionsData = listOf(
        QuestionData(
            question = "איזה יום היום?",
            answers = listOf("ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת")
        ),
        QuestionData(
            question = "באיזה חודש אנחנו נמצאים?",
            answers = listOf("ינואר", "פברואר", "מרץ", "אפריל", "מאי", "יוני", "יולי", "אוגוסט", "ספטמבר", "אוקטובר", "נובמבר", "דצמבר")
        ),
        QuestionData(
            question = "באיזו שנה אנחנו נמצאים?",
            answers = listOf("1994", "2024", "1964", "2025", "2000", "2023", "1924", "1948")
        ),
        QuestionData(
            question = "באיזו עיר אתה נמצא כרגע?",
            answers = listOf("גני תקווה", "חיפה", "תל אביב", "פתח תקווה", "ראשון לציון", "כפר סבא", "ראש העין", "בני ברק", "ירושלים", "באר שבע")
        ),
        QuestionData(
            question = "באיזו מדינה אתה גר?",
            answers = listOf("מרוקו", "ארצות הברית", "עיראק", "לבנון", "ישראל", "תימן", "רוסיה", "אתיופיה")
        ),
        QuestionData(
            question = "באיזה איזור בישראל אתה נמצא?",
            answers = listOf("מרכז", "צפון", "דרום", "הגליל המערבי", "צפון השרון", "מישור החוף")
        ),
        QuestionData(
            question = "למה אתה עונה לשאלון הזה?",
            answers = listOf("אני לא יודע", "כי אני בודק את תפקודיהחשיבה שלי", "אמרו לי להגיע", "זו פעילות שגרתית עבורי")
        )
    )

    questionsData.forEach { questionData ->
        TimeAndPlaceQuestion(
            question = questionData.question,
            dropDownItems = questionData.answers.map { DropDownItem(it) },
            viewModel = viewModel,
            onItemClick = { selectedAnswer ->
                viewModel.setAnswers(questionData.question, selectedAnswer)
            }
        )
    }
}

data class QuestionData(val question: String, val answers: List<String>)
