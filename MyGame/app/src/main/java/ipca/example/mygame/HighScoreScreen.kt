package ipca.example.mygame

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HighScoreScreen(context: Context, onReturnToMenu: () -> Unit) {
    val scoreManager = ScoreManager(context)
    val highScore = scoreManager.getHighScore()

    Box(modifier = Modifier.fillMaxSize()) {
        // Exibir a imagem de fundo e ajustá-la para preencher o ecrã
        Image(
            painter = painterResource(id = R.drawable.main_screen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "High Score", fontSize = 30.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "$highScore", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onReturnToMenu) {
                Text("Return to Menu")
            }
        }
    }
}
