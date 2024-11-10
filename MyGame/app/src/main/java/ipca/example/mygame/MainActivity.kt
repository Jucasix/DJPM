package ipca.example.mygame

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Força o modo horizontal
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        setContent {
            MainActivityScreen(context = this)
        }
    }
}

@Composable
fun MainActivityScreen(context: Context) {
    // Controla qual tela está sendo exibida
    var showGameScreen by remember { mutableStateOf(false) }
    var showHighScoreScreen by remember { mutableStateOf(false) }

    when {
        showGameScreen -> {
            GameScreen(context = context) { showGameScreen = false }
        }
        showHighScoreScreen -> {
            HighScoreScreen(context = context) { showHighScoreScreen = false }
        }
        else -> {
            MainMenuScreen(
                context = context,
                onPlayClick = { showGameScreen = true },
                onHighScoreClick = { showHighScoreScreen = true }
            )
        }
    }
}
