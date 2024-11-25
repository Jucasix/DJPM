package ipca.example.mygame

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas

class Player(context: Context, screenWidth: Int, private val screenHeight: Int) {

    var bitmap: Bitmap
    var x: Float = 50f
    var y: Float
    private var velocityY = 0f
    private var isJumping = false
    private var isRunning = false
    private val runningSpeed = 15f
    private val returnSpeed = 5f

    init {
        // Carrega o bitmap original do jogador
        val originalBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.player)

        // Define uma proporção razoável do tamanho do ecrã para o jogador
        val playerWidth = (screenWidth * 0.35).toInt()  // Ajusta a largura para 10% do ecrã
        val aspectRatio = originalBitmap.height.toFloat() / originalBitmap.width.toFloat()
        val playerHeight = (playerWidth * aspectRatio).toInt()  // Mantém a proporção do jogador

        // Cria o bitmap redimensionado do jogador
        bitmap = Bitmap.createScaledBitmap(originalBitmap, playerWidth, playerHeight, true)

        // Define a posição inicial do jogador no eixo Y
        y = (screenHeight - bitmap.height - 100).toFloat()
    }

    fun isCollidingWith(obstacle: ObstacleBase): Boolean {
        val playerRight = x + bitmap.width
        val playerBottom = y + bitmap.height
        val obstacleRight = obstacle.xPosition + obstacle.obstacleImage.width
        val obstacleBottom = obstacle.yPosition + obstacle.obstacleImage.height

        return playerRight > obstacle.xPosition && x < obstacleRight &&
                playerBottom > obstacle.yPosition && y < obstacleBottom
    }

    fun update(screenHeight: Int) {
        // Atualiza a posição vertical para salto
        if (isJumping) {
            velocityY += 2  // Aumenta a gravidade para tornar o salto mais rápido
            y += velocityY
            if (y >= screenHeight - bitmap.height - 100) {
                y = (screenHeight - bitmap.height - 100).toFloat()
                isJumping = false
                velocityY = 0f
            }
        }

        // Atualiza a posição horizontal para corrida
        if (isRunning) {
            x += runningSpeed
        } else if (!isJumping && y >= screenHeight - bitmap.height - 100) {
            // Move o player de volta para a posição inicial apenas quando está na relva
            x -= returnSpeed
            if (x < 50f) {
                x = 50f // Garante que não ultrapasse a posição inicial
            }
        }
    }

    fun jump() {
        if (!isJumping) {
            velocityY = -50f  // Aumenta a força do salto para ser mais rápido
            isJumping = true
        }
    }

    fun startRunning() {
        isRunning = true
    }

    fun stopRunning() {
        isRunning = false
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, x, y, null)
    }
}
