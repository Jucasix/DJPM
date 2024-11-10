package ipca.example.mygame

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Forçar o modo horizontal
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        setContent {
            var showGameScreen by remember { mutableStateOf(false) }
            var showHighScoreScreen by remember { mutableStateOf(false) }

            if (showGameScreen) {
                GameScreen(this)
            } else if (showHighScoreScreen) {
                // Implementação do ecrã de High Score
            } else {
                MainMenuScreen(
                    context = this,
                    onPlayClick = { showGameScreen = true },
                    onHighScoreClick = { showHighScoreScreen = true }
                )
            }
        }
    }
}
