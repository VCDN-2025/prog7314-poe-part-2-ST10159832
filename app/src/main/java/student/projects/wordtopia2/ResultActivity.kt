package student.projects.wordtopia2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import student.projects.wordtopia2.databinding.ActivityResultBinding
import kotlin.jvm.java

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.gameoverImage.setImageResource(R.drawable.gameover_image)

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
