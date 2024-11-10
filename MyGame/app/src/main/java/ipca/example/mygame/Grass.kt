package ipca.example.mygame

import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.imageResource

class Grass(context: Context) {
    private val grassImage: ImageBitmap = ImageBitmap.imageResource(context.resources, R.drawable.grass) // Imagem separada da relva

    fun draw(drawScope: DrawScope, groundYPosition: Float, screenWidth: Float, scale: Float = 4f) {
        drawScope.drawIntoCanvas { canvas ->
            val scaledWidth = grassImage.width * scale
            val scaledHeight = grassImage.height * scale

            for (i in 0 until (screenWidth / scaledWidth).toInt() + 1) {
                canvas.nativeCanvas.drawBitmap(
                    grassImage.asAndroidBitmap(),
                    null,
                    android.graphics.RectF(i * scaledWidth, groundYPosition, (i + 1) * scaledWidth, groundYPosition + scaledHeight),
                    null
                )
            }
        }
    }
}
