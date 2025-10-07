package student.projects.wordtopia2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import student.projects.wordtopia2.databinding.ActivityResultBinding
import student.projects.wordtopia2.utils.ThemeManager

class ResultActivity : BaseActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeManager.applyTheme(this)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val won = intent.getBooleanExtra("won", false)
        val score = intent.getIntExtra("score", 0)
        val word = intent.getStringExtra("word") ?: ""


        if (won) {
            binding.gameoverImage.setImageResource(R.drawable.gameover_image) // You can replace with your own drawable
        } else {
            binding.gameoverImage.setImageResource(R.drawable.gameover_image)
        }


        val resultText = if (won) {
            "YOU WON! WORD: $word    SCORE: $score POINTS"
        } else {
            "YOU LOST! WORD: $word    SCORE: $score POINTS"
        }

        binding.txtWordScore.text = resultText


        binding.btnPlayAgain.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
            finish()
        }


        binding.btnHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
