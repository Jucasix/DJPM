package ipca.example.mygame

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.graphics.drawable.toBitmap
import kotlin.random.Random

open class ObstacleBase(context: Context, imageResId: Int, startX: Float) {

    val obstacleImage: Bitmap = context.resources.getDrawable(imageResId, null).toBitmap()
    var xPosition = startX
    var yPosition = 0f  // Posição Y ajustável para os obstáculos
    var speed = 5f  // Velocidade do obstáculo, ajustável para aumentar a dificuldade

    // Método para obter a altura do obstáculo
    fun getObstacleHeight(): Int {
        return obstacleImage.height
    }

    fun update() {
        xPosition -= speed  // Move o obstáculo com base na velocidade
        if (xPosition < 0) {
            xPosition = Random.nextFloat() * 1500 + 500
        }
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(obstacleImage, xPosition, yPosition, null)
    }
}

// Classes específicas para cada tipo de obstáculo
class ObstacleB(context: Context, startX: Float) : ObstacleBase(context, R.drawable.obstacleb, startX)
class ObstacleL(context: Context, startX: Float) : ObstacleBase(context, R.drawable.obstaclel, startX)
class ObstacleM(context: Context, startX: Float) : ObstacleBase(context, R.drawable.obstaclem, startX)
