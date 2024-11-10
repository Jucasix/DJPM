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
        get() = playerBitmap.height // Altura do Bitmap do player
    var xPosition by mutableStateOf(100f)
    var yPosition by mutableStateOf(600f)
    private val jumpHeight = 300f
    private var isJumping by mutableStateOf(false)
    private var yVelocity by mutableStateOf(0f)

    fun update() {
        if (isJumping) {
            yVelocity -= 10
            yPosition -= yVelocity
            if (yPosition >= 600f) {
                yPosition = 600f
                isJumping = false
                yVelocity = 0f
            }
        }
    }

    fun jump() {
        if (!isJumping) {
            isJumping = true
            yVelocity = 30f
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

    fun getBounds() = android.graphics.Rect(
        xPosition.toInt(),
        yPosition.toInt(),
        xPosition.toInt() + playerBitmap.width,
        yPosition.toInt() + playerBitmap.height
    )
}
