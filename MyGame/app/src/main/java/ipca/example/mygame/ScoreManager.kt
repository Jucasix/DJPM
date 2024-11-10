package ipca.example.mygame

import android.content.Context
import android.content.SharedPreferences

class ScoreManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("game_prefs", Context.MODE_PRIVATE)

    fun saveHighScore(score: Int) {
        val highScore = getHighScore()
        if (score > highScore) {
            prefs.edit().putInt("HIGH_SCORE", score).apply()
        }
    }

    fun getHighScore(): Int {
        return prefs.getInt("HIGH_SCORE", 0)
    }
}
