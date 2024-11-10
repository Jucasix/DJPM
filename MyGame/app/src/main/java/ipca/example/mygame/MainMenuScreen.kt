package ipca.example.mygame

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp

@Composable
fun MainMenuScreen(context: Context, onPlayClick: () -> Unit, onHighScoreClick: () -> Unit) {
    val background = ImageBitmap.imageResource(id = R.drawable.main_screen)

    Canvas(modifier = Modifier.fillMaxSize()) {
        // Desenhar o fundo
        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.drawBitmap(
                background.asAndroidBitmap(),
                android.graphics.Rect(0, 0, background.width, background.height),
                android.graphics.RectF(0f, 0f, size.width, size.height),
                null
            )
        }
    }

    // Exibir os botões "PLAY NOW" e "HIGH SCORE"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onPlayClick) {
            Text("PLAY NOW")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onHighScoreClick) {
            Text("HIGH SCORE")
        }
    }
}

