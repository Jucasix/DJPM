package ipca.example.mygame

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun GameScreen(context: Context, onReturnToMenu: () -> Unit) {
    // Gerenciador de score
    val scoreManager = ScoreManager(context)

    // Variáveis de controle de dificuldade e velocidade
    var obstacleSpeed by remember { mutableStateOf(5f) }
    var difficultyLevel by remember { mutableStateOf(1) }
    var lastIncreaseTime by remember { mutableStateOf(System.currentTimeMillis()) }
    var isGameOver by remember { mutableStateOf(false) }

    // Variável para o tempo decorrido
    var elapsedTime by remember { mutableStateOf(0) }

    // Atualizar o tempo e pontuação a cada segundo
    LaunchedEffect(Unit) {
        while (!isGameOver) {
            delay(1000L) // Aguardar um segundo
            elapsedTime += 1
        }
    }

    val background = ImageBitmap.imageResource(id = R.drawable.main_screen)
    val grass = remember { Grass(context) }
    val player = remember { Player(context) }

    // Estado para lista de obstáculos, que será atualizada com padrões diferentes ao aumentar a dificuldade
    var obstacles by remember { mutableStateOf(getObstaclePattern(context, difficultyLevel)) }

    if (isGameOver) {
        // Salva o High Score e exibe a tela de Game Over
        scoreManager.saveHighScore(elapsedTime)
        GameOverScreen(context = context, onReturnToMenu = onReturnToMenu)
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            // Canvas com o jogo
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = { player.jump() })
                    }
            ) {
                // Desenhar o fundo
                drawIntoCanvas { canvas ->
                    canvas.nativeCanvas.drawBitmap(
                        background.asAndroidBitmap(),
                        android.graphics.Rect(0, 0, background.width, background.height),
                        android.graphics.RectF(0f, 0f, size.width, size.height),
                        null
                    )
                }

                // Definir a posição do chão
                val groundYPosition = size.height - 32.dp.toPx()

                // Desenhar a relva
                grass.draw(this, groundYPosition, size.width, scale = 4f)

                // Ajustar a posição do player para correr sobre a relva
                player.yPosition = groundYPosition - player.height * 4
                player.update()
                player.draw(this, scale = 4f)

                // Atualizar dificuldade a cada minuto
                if (System.currentTimeMillis() - lastIncreaseTime >= 30000) {
                    obstacleSpeed += 2f // Aumenta a velocidade dos obstáculos
                    difficultyLevel += 1 // Aumenta o nível de dificuldade
                    lastIncreaseTime = System.currentTimeMillis() // Reseta o contador de tempo

                    // Atualiza a lista de obstáculos com o novo padrão
                    obstacles = getObstaclePattern(context, difficultyLevel)
                }

                // Variável para armazenar a posição x do obstáculo anterior
                var previousObstacleX: Float? = null

                // Desenhar e atualizar cada obstáculo
                obstacles.forEach { obstacle ->
                    obstacle.setGroundPosition(groundYPosition)
                    obstacle.update(previousObstacleX, size.width, obstacleSpeed)
                    obstacle.draw(this)

                    // Verificação de colisão com o player
                    if (checkCollision(player, obstacle)) {
                        isGameOver = true // Define o estado como "Game Over"
                    }

                    previousObstacleX = obstacle.xPosition
                }
            }

            // Exibir o tempo decorrido e a pontuação no canto superior esquerdo, fora do Canvas
            Text(
                text = "Tempo: ${elapsedTime}s",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            )
        }
    }
}

// Função para obter o padrão de obstáculos com base no nível de dificuldade
fun getObstaclePattern(context: Context, difficultyLevel: Int): List<ObstacleBase> {
    return when (difficultyLevel) {
        1 -> listOf(ObstacleB(context, startX = 1080f)) // Padrão simples
        2 -> listOf(
            ObstacleB(context, startX = 1080f),
            ObstacleL(context, startX = 1300f)
        ) // Padrão médio
        3 -> listOf(
            ObstacleB(context, startX = 1080f),
            ObstacleL(context, startX = 1300f),
            ObstacleM(context, startX = 1600f)
        ) // Padrão avançado
        else -> listOf(
            ObstacleB(context, startX = 1080f),
            ObstacleL(context, startX = 1200f),
            ObstacleM(context, startX = 1400f),
            ObstacleB(context, startX = 1600f)
        ) // Padrão mais difícil
    }
}

// Função para verificar a colisão do jogador com um obstáculo
fun checkCollision(player: Player, obstacle: ObstacleBase): Boolean {
    val playerBounds = player.getBounds()
    val obstacleBounds = obstacle.getBounds()

    // Colisão lateral - se o jogador toca os lados do obstáculo, ele perde
    return if (playerBounds.intersect(obstacleBounds)) {
        player.yPosition + player.height < obstacle.yPosition + 10 // Permite pular sobre o obstáculo
    } else false
}
