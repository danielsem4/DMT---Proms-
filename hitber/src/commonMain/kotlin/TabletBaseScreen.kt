
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.hit.heal.core.presentation.Colors.backgroundColor
import org.example.hit.heal.core.presentation.Colors.primaryColor

@Composable
fun TabletBaseScreen(
    title: String,
    onNextClick: (() -> Unit)? = null,
    buttonText: String,
    question: Int,
    buttonColor : Color,
    content: @Composable() (ColumnScope.() -> Unit)
) {
    MaterialTheme {

        val statusBarValues = WindowInsets.safeDrawing.asPaddingValues()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            // Top Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryColor)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(top = statusBarValues.calculateTopPadding())
                )
            }

            // Dynamic Content
            Column(
                modifier = Modifier.padding(8.dp).weight(0.85f)
            ) {
                content()
            }

            // Bottom Navigation Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
            ) {

                    if (onNextClick != null) {
                        Button(
                            onClick = onNextClick,
                            colors = ButtonDefaults.buttonColors(buttonColor),
                            shape = RoundedCornerShape(30),
                            modifier = Modifier.width(300.dp).height(50.dp).align(Alignment.Center)
                        ) {
                            Text(buttonText, color = Color.White, fontSize = 25.sp)
                        }
                    }


                    Text(
                        text = "${question}/10",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor,
                        modifier = Modifier.padding(end = 16.dp).align(Alignment.BottomEnd)
                    )

            }}

        }
    }





