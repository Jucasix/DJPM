package ipca.example.calculater.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalcButton(
    text: String,
    fontSize: TextUnit = 24.sp,
    onClick: () -> Unit,
    isOperator: Boolean = false
) {
    val buttonColor = if (isOperator) Color.DarkGray else Color.Gray

    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        modifier = Modifier
            .padding(8.dp)
            .size(64.dp)
    ) {
        Text(text = text, color = Color.White, fontSize = fontSize)
    }
}