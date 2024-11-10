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

    fun setGroundPosition(groundYPosition: Float) {
        yPosition = groundYPosition - obstacleImage.height
    }

    fun update() {
        xPosition -= 10 // Movimento para a esquerda
        if (xPosition < -obstacleImage.width) {
            xPosition = Random.nextFloat() * 300 + 1080f // Reposição aleatória
        }
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
