package ipca.example.mygame

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun GameScreen(context: Context) {
    val density = LocalDensity.current
    val background = ImageBitmap.imageResource(id = R.drawable.main_screen)

    val grass = remember { Grass(context) }

    val obstacles = remember {
        listOf(
            ObstacleB(context, startX = Random.nextFloat() * 300 + 1080f),
            ObstacleL(context, startX = Random.nextFloat() * 300 + 1380f),
            ObstacleM(context, startX = Random.nextFloat() * 300 + 1600f)
        )
    }

    val player = remember { Player(context) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { player.jump() })
            }
    ) {
        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.drawBitmap(
                background.asAndroidBitmap(),
                android.graphics.Rect(0, 0, background.width, background.height),
                android.graphics.RectF(0f, 0f, size.width, size.height),
                null
            )
        }

        val groundYPosition = size.height - 32.dp.toPx()
        grass.draw(this, groundYPosition, size.width, scale = 4f)

        player.yPosition = groundYPosition - player.height * 4
        player.update()
        player.draw(this, scale = 4f)

        obstacles.forEach { obstacle ->
            obstacle.setGroundPosition(groundYPosition)
            obstacle.update()
            obstacle.draw(this)
        }
    }
}
