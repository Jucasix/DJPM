package ipca.example.mygame

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import ipca.example.mygame.R

class Player(context: Context, screenWidth: Int, private val screenHeight: Int) {

    var bitmap: Bitmap
    var x: Float = 50f
    var y: Float
    private var velocityY = 0f
    private var isJumping = false

    init {
        // Carregar o bitmap e redimensionar de acordo com screenWidth e screenHeight
        val originalBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.player)
        val playerWidth = (screenWidth * 0.25).toInt()
        val playerHeight = (screenHeight * 0.2).toInt()
        bitmap = Bitmap.createScaledBitmap(originalBitmap, playerWidth, playerHeight, false)

        // Definir a posição inicial do player
        y = (screenHeight - bitmap.height - 100).toFloat()
    }

    // Verifica colisão com um obstáculo
    fun isCollidingWith(obstacle: ObstacleBase): Boolean {
        val playerRight = x + bitmap.width
        val playerBottom = y + bitmap.height
        val obstacleRight = obstacle.xPosition + obstacle.obstacleImage.width
        val obstacleBottom = obstacle.yPosition + obstacle.obstacleImage.height

        return playerRight > obstacle.xPosition && x < obstacleRight &&
                playerBottom > obstacle.yPosition && y < obstacleBottom
    }

    fun update(screenHeight: Int) {
        if (isJumping) {
            velocityY += 1 // Gravidade simulada
            y += velocityY
            if (y >= screenHeight - bitmap.height - 100) {
                y = (screenHeight - bitmap.height - 100).toFloat()
                isJumping = false
                velocityY = 0f
            }
        }
    }

    fun jump() {
        if (!isJumping) {
            velocityY = -20f  // Ajusta a força do salto
            isJumping = true
        }
    }

    fun startRunning() {
        x += 5  // Move o player para a frente ao correr
    }

    fun stopRunning() {
        x = 50f  // Volta à posição inicial ao parar de correr
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, x, y, null)
    }
}
