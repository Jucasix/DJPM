package ipca.example.mygame

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp

@Composable
fun GameScreen(context: Context) {
    val density = LocalDensity.current

    // Carregar a imagem de fundo do início
    val background = ImageBitmap.imageResource(id = R.drawable.main_screen)

    // Carregar a imagem do chão (spritesheet)
    val groundImage = ImageBitmap.imageResource(id = R.drawable.jungletileset)

    // Criar a lista de obstáculos
    val obstacles = remember {
        listOf(
            Obstacle(context, startX = 1080f, density = density),
            Obstacle(context, startX = 1600f, density = density)
        )
    }

    // Criar o jogador
    val player = remember { Player(context) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    player.jump()
                })
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

        // Definir a posição do chão na parte inferior do ecrã
        val groundYPosition = size.height - 64.dp.toPx() // Posição do chão na parte inferior

        // Ajustar a posição do jogador para a parte inferior e aumentar o tamanho
        player.yPosition = groundYPosition - player.height.toFloat() // Ajusta o player para correr sobre a relva
        player.update()
        player.draw(this, scale = 2f) // Aumentar o tamanho do player

        // Atualizar e desenhar cada obstáculo
        obstacles.forEach { obstacle ->
            obstacle.update()
            obstacle.draw(this)

            // Verificação de colisão
            if (player.getBounds().intersect(obstacle.getBounds())) {
                // Lógica para o jogador perder
            }
        }

        // Desenhar o chão a partir da spritesheet
        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.drawBitmap(
                groundImage.asAndroidBitmap(),
                android.graphics.Rect(0, 0, 64, 64),
                android.graphics.RectF(0f, groundYPosition, size.width, size.height),
                null
            )
        }
    }
}