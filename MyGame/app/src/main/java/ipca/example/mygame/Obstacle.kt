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
import kotlin.random.Random

open class ObstacleBase(context: Context, imageResId: Int, startX: Float) {
    private val obstacleImage: ImageBitmap = ImageBitmap.imageResource(context.resources, imageResId)
    var xPosition by mutableStateOf(startX)
    var yPosition by mutableStateOf(0f) // Ajustado posteriormente na posição do chão

    private val obstacleWidthPx = obstacleImage.width

    // Distância mínima entre obstáculos
    private val minDistanceBetweenObstacles = 500f

    // Atraso inicial extra para colocar os obstáculos bem fora do ecrã no início do jogo
    private var initialDelay = Random.nextInt(500, 1500).toFloat()

    fun setGroundPosition(groundYPosition: Float) {
        yPosition = groundYPosition - obstacleImage.height
    }

    fun update(previousObstacleX: Float?, screenWidth: Float, obstacleSpeed: Float) {
        if (initialDelay > 0) {
            initialDelay -= 10
            xPosition = screenWidth + initialDelay
        } else {
            xPosition -= obstacleSpeed

            if (xPosition < -obstacleWidthPx) {
                val startX = (previousObstacleX ?: screenWidth) + minDistanceBetweenObstacles + Random.nextFloat() * 200
                xPosition = startX
            }
        }
    }

    fun getBounds(): android.graphics.Rect {
        return android.graphics.Rect(
            xPosition.toInt(),
            yPosition.toInt(),
            (xPosition + obstacleImage.width).toInt(),
            (yPosition + obstacleImage.height).toInt()
        )
    }

    fun draw(drawScope: DrawScope) {
        drawScope.drawIntoCanvas { canvas ->
            canvas.nativeCanvas.drawBitmap(
                obstacleImage.asAndroidBitmap(),
                null,
                android.graphics.RectF(xPosition, yPosition, xPosition + obstacleImage.width, yPosition + obstacleImage.height),
                null
            )
        }
    }
}

// Classes específicas para cada tipo de obstáculo
class ObstacleB(context: Context, startX: Float) : ObstacleBase(context, R.drawable.obstacleb, startX)
class ObstacleL(context: Context, startX: Float) : ObstacleBase(context, R.drawable.obstaclel, startX)
class ObstacleM(context: Context, startX: Float) : ObstacleBase(context, R.drawable.obstaclem, startX)
