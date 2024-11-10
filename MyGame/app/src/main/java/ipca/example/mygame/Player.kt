package ipca.example.mygame

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.imageResource

class Player(context: Context) {
    private val playerBitmap: ImageBitmap = ImageBitmap.imageResource(context.resources, R.drawable.player)
    val height: Int
        get() = playerBitmap.height
    var xPosition by mutableStateOf(100f)
    var yPosition by mutableStateOf(600f)

    // Ajustes de controle para salto fluido
    var yVelocity = 0f
    private val initialJumpStrength = 10f // Reduzida para uma subida mais lenta
    private val gravity = 0.4f // Gravidade mais leve para uma descida mais suave
    private val maxJumpHeight = 200f
    private val terminalVelocity = 8f // Limite da velocidade de descida mais baixo para suavizar a queda

    var isJumping by mutableStateOf(false)
    private var isGoingUp = true // Controla a direção do salto

    fun update() {
        if (isJumping) {
            if (isGoingUp) {
                // Subida
                yVelocity -= gravity
                yPosition -= yVelocity

                // Verifica se atingiu o topo do salto
                if (yPosition <= 600f - maxJumpHeight || yVelocity <= 0f) {
                    isGoingUp = false // Inicia a descida
                }
            } else {
                // Descida
                yVelocity += gravity // Gravidade aplicada durante a descida
                if (yVelocity > terminalVelocity) {
                    yVelocity = terminalVelocity // Limita a velocidade de descida
                }
                yPosition += yVelocity
            }

            // Retornar ao chão
            if (yPosition >= 600f) {
                yPosition = 600f
                yVelocity = 0f
                isJumping = false
                isGoingUp = true // Reset para próximo salto
            }
        }
    }

    fun jump() {
        if (!isJumping) {
            isJumping = true
            isGoingUp = true
            yVelocity = initialJumpStrength // Define a força inicial para a subida
        }
    }

    fun draw(drawScope: DrawScope, scale: Float = 4f) {
        drawScope.drawIntoCanvas { canvas ->
            val scaledWidth = playerBitmap.width * scale
            val scaledHeight = playerBitmap.height * scale
            canvas.nativeCanvas.drawBitmap(
                playerBitmap.asAndroidBitmap(),
                null,
                android.graphics.RectF(
                    xPosition,
                    yPosition,
                    xPosition + scaledWidth,
                    yPosition + scaledHeight
                ),
                null
            )
        }
    }

    fun getBounds(): android.graphics.Rect {
        val scaleFactor = 4f // Certifique-se de que o fator de escala é o mesmo que o usado para desenhar o jogador
        return android.graphics.Rect(
            xPosition.toInt(),
            yPosition.toInt(),
            (xPosition + playerBitmap.width * scaleFactor).toInt(),
            (yPosition + playerBitmap.height * scaleFactor).toInt()
        )
    }
}
