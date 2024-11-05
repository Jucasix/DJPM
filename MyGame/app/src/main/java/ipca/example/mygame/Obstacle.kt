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
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp

class Obstacle(context: Context, startX: Float, density: Density) {
    private val obstacleBitmap: ImageBitmap = ImageBitmap.imageResource(context.resources, R.drawable.jungletileset)
    var xPosition by mutableStateOf(startX)
    private val yPosition = 600f // Nível do chão
    private val obstacleWidthPx = with(density) { 64.dp.toPx() } // Converte 64.dp para pixels

    fun update() {
        xPosition -= 10 // Movimento para a esquerda
        if (xPosition < -obstacleWidthPx) {
            xPosition = 1080f // Reaparece no lado direito
        }
    }

    fun draw(drawScope: DrawScope) {
        drawScope.drawIntoCanvas { canvas ->
            // Define a parte da spritesheet para usar como obstáculo
            val srcX = 128f // Ajusta para coordenadas corretas da spritesheet
            val srcY = 0f
            val srcWidth = 64f
            val srcHeight = 64f

            canvas.nativeCanvas.drawBitmap(
                obstacleBitmap.asAndroidBitmap(),
                android.graphics.Rect(srcX.toInt(), srcY.toInt(), (srcX + srcWidth).toInt(), (srcY + srcHeight).toInt()),
                android.graphics.RectF(xPosition, yPosition, xPosition + srcWidth, yPosition + srcHeight),
                null
            )
        }
    }

    fun getBounds() = android.graphics.Rect(
        xPosition.toInt(),
        yPosition.toInt(),
        xPosition.toInt() + obstacleWidthPx.toInt(),
        yPosition.toInt() + obstacleBitmap.height
    )
}
