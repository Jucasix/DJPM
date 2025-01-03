package ipca.example.mygame

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale

@Composable
fun MainMenuScreen(context: Context, onPlayClick: () -> Unit, onHighScoreClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Exibir a imagem de fundo e ajustá-la para preencher o ecrã
        Image(
            painter = painterResource(id = R.drawable.main_screen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Conteúdo do menu
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = onPlayClick) {
                Text(text = "PLAY NOW", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onHighScoreClick) {
                Text(text = "HIGH SCORE", fontSize = 18.sp)
            }
        }
    }
}
