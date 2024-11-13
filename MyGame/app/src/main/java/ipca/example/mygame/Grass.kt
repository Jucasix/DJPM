package ipca.example.mygame

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.graphics.drawable.toBitmap

class Grass(context: Context, screenWidth: Int, screenHeight: Int) {

    private val grassBitmap: Bitmap = Bitmap.createScaledBitmap(
        context.resources.getDrawable(R.drawable.grass, null).toBitmap(),
        screenWidth,
        screenHeight,
        false
    )

    // Método para obter a altura da imagem da relva
    fun getHeight(): Int {
        return grassBitmap.height
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(grassBitmap, 0f, (canvas.height - grassBitmap.height).toFloat(), null)
    }
}

