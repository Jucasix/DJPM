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
    private val runningSpeed = 10f
    private val returnSpeed = 5f // Velocidade para voltar à posição inicial

    init {
        val originalBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.player)
        val playerWidth = (screenWidth * 0.25).toInt()
        val playerHeight = (screenHeight * 0.2).toInt()
        bitmap = Bitmap.createScaledBitmap(originalBitmap, playerWidth, playerHeight, false)

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
            velocityY += 1 // Gravidade simulada
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
            velocityY = -30f  // Aumenta a força do salto
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
