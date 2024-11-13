package ipca.example.mygame

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController

@Composable
fun GameScreen(context: Context, navController: NavController) {
    // Obtém a configuração atual da tela
    val configuration = LocalConfiguration.current
    val density = context.resources.displayMetrics.density
    val screenWidthPx = (configuration.screenWidthDp * density).toInt()
    val screenHeightPx = (configuration.screenHeightDp * density).toInt()

    // Configuração do GameView com o NavController
    AndroidView(
        factory = { ctx ->
            GameView(context = ctx, screenWidth = screenWidthPx, screenHeight = screenHeightPx, navController = navController)
        },
        update = {
            it.resume() // Função para iniciar ou reiniciar o jogo
        }
    )
}
