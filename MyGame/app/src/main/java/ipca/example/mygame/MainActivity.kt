package ipca.example.mygame

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ipca.example.mygame.ui.theme.MyGameTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configura a orientação em modo paisagem (horizontal)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            MyGameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    NavHost(navController = navController, startDestination = "main_menu") {
                        composable("main_menu") {
                            MainMenuScreen(
                                context = this@MainActivity,
                                onPlayClick = { navController.navigate("game_screen") },
                                onHighScoreClick = { navController.navigate("high_score_screen") }
                            )
                        }
                        composable("game_screen") {
                            GameScreen(
                                context = this@MainActivity,
                                navController = navController
                            )
                        }
                        composable("high_score_screen") {
                            HighScoreScreen(
                                context = this@MainActivity,
                                onReturnToMenu = { navController.popBackStack("main_menu", inclusive = false) }
                            )
                        }
                        composable("game_over") {
                            GameOverScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}



