
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.hit.heal.core.presentation.BaseScreen
import org.example.hit.heal.core.presentation.Colors

@Composable
fun QuestionnaireScreen(onNextClick: (() -> Unit)? = null, onPrevClick: (() -> Unit)? = null) {
    var selectedOption by remember { mutableStateOf("") }
    val options = listOf("Great!", "Good", "In pain", "Lazy", "Other")

    BaseScreen(
        "Questionnaire", onPrevClick, onNextClick
    ) {
        // Question
        Text(
            text = "How do you feel?",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Options
        options.forEach { option ->
            OptionItem(
                option = option,
                isSelected = selectedOption == option,
                onSelect = { selectedOption = option }
            )
        }
    }
}

@Composable
fun OptionItem(option: String, isSelected: Boolean, onSelect: () -> Unit) {
    // Set background, text, and icon colors based on the selection state
    val backgroundColor = if (isSelected) Colors.primaryColor else Color.White
    val textColor = if (isSelected) Color.White else Color.Black
    val iconTint = if (isSelected) Color.White else Colors.primaryColor

    // Set icon based on selection state
    val icon = if (isSelected) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .background(backgroundColor, shape = RoundedCornerShape(32.dp))
            .padding(16.dp)
            .clickable { onSelect() }, // Make the row clickable
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon with static tint
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        // Text with static color
        Text(
            text = option,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}


