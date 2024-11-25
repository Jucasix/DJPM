package ipca.example.mygame

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.navigation.NavController
import kotlin.random.Random

class GameView(context: Context, private val screenWidth: Int, private val screenHeight: Int, private val navController: NavController) : SurfaceView(context), SurfaceHolder.Callback, Runnable {

    private var playing = false
    private var gameThread: Thread? = null
    private lateinit var player: Player
    private var obstacles: MutableList<ObstacleBase> = mutableListOf()
    private lateinit var surfaceHolder: SurfaceHolder
    private var obstacleSpeed = 5f
    private var grass: Grass? = null
    private var backgroundBitmap: Bitmap? = null
    private var gameStartTime: Long = 0
    private var elapsedTime: Long = 0
    private val paint = Paint().apply {
        color = Color.WHITE
        textSize = 60f
    }
    private val scoreManager = ScoreManager(context)

    init {
        surfaceHolder = holder
        surfaceHolder.addCallback(this)

        backgroundBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.main_screen)?.let {
            Bitmap.createScaledBitmap(it, screenWidth, screenHeight, false)
        }

        grass = Grass(context, screenWidth, screenHeight / 4)
        val groundLevelY = screenHeight - (grass?.getHeight() ?: 0).toFloat()

        player = Player(context, (screenWidth * 0.20).toInt(), (screenHeight * 0.25).toInt())
        player.y = groundLevelY - player.bitmap.height

        var previousX = screenWidth.toFloat() + 500
        val minSpacing = 600f
        for (i in 0 until 3) {
            previousX += minSpacing + Random.nextInt(500, 1500).toFloat()
            val obstacle = ObstacleB(context, previousX)
            obstacle.yPosition = groundLevelY - obstacle.getObstacleHeight().toFloat()
            obstacles.add(obstacle)
        }

        gameStartTime = System.currentTimeMillis()
    }

    override fun run() {
        while (playing) {
            val startTime = System.currentTimeMillis()

            updateGame()
            drawGame()
            checkCollisions()
            checkDifficultyIncrease()
            updateElapsedTime()

            val frameTime = System.currentTimeMillis() - startTime
            val sleepTime = (33 - frameTime).coerceAtLeast(0)
            Thread.sleep(sleepTime)
        }
    }

    private fun updateGame() {
        player.update(screenHeight)
        obstacles.forEach { it.update() }
    }

    private fun drawGame() {
        val canvas = surfaceHolder.lockCanvas()
        if (canvas != null) {
            try {
                backgroundBitmap?.let {
                    canvas.drawBitmap(it, 0f, 0f, null)
                } ?: run {
                    canvas.drawColor(Color.BLACK)
                }

                grass?.draw(canvas)
                player.draw(canvas)
                obstacles.forEach { it.draw(canvas) }

                canvas.drawText("Time: ${elapsedTime / 1000}s", 50f, 100f, paint)
            } finally {
                surfaceHolder.unlockCanvasAndPost(canvas)
            }
        }
    }

    private fun checkCollisions() {
        for (obstacle in obstacles) {
            if (player.isCollidingWith(obstacle)) {
                val playerBottom = player.y + player.bitmap.height
                val obstacleTop = obstacle.yPosition
                val playerRight = player.x + player.bitmap.width
                val obstacleLeft = obstacle.xPosition
                val obstacleRight = obstacle.xPosition + obstacle.obstacleImage.width

                if (playerBottom <= obstacleTop + 10 && playerRight > obstacleLeft && player.x < obstacleRight) {
                    continue
                }

                scoreManager.saveHighScore((elapsedTime / 1000).toInt())
                playing = false
                Handler(Looper.getMainLooper()).post {
                    navController.navigate("game_over")
                }
                break
            }
        }
    }

    private fun checkDifficultyIncrease() {
        if (elapsedTime / 1000 % 30 == 0L && elapsedTime > 0) {
            obstacleSpeed += 2f
            obstacles.forEach { it.speed = obstacleSpeed }
        }
    }

    private fun updateElapsedTime() {
        elapsedTime = System.currentTimeMillis() - gameStartTime
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                player.jump()
                player.startRunning()
            }
            MotionEvent.ACTION_UP -> {
                player.stopRunning()
            }
        }
        return true
    }

    fun resume() {
        playing = true
        gameThread = Thread(this)
        gameThread?.start()
    }

    fun pause() {
        playing = false
        gameThread?.join()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        resume()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        pause()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
}
