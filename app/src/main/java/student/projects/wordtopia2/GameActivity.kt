package student.projects.wordtopia2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import student.projects.wordtopia2.databinding.ActivityGameBinding
import kotlin.apply
import kotlin.collections.contains
import kotlin.collections.joinToString
import kotlin.jvm.java
import kotlin.text.contains
import kotlin.text.indices

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private val word = "WALL"
    private var guessed = CharArray(word.length) { '_' }
    private var mistakes = 0
    private val maxMistakes = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupKeyboard()
        updateWordDisplay()
    }

    private fun setupKeyboard() {
        val letters = ('A'..'Z')
        for (c in letters) {
            val btn = Button(this).apply {
                text = c.toString()
                setOnClickListener { handleGuess(c, this) }
            }
            binding.letterGrid.addView(btn)
        }
    }

    private fun handleGuess(letter: Char, btn: Button) {
        btn.isEnabled = false
        if (word.contains(letter)) {
            for (i in word.indices) {
                if (word[i] == letter) guessed[i] = letter
            }
            updateWordDisplay()
            if (!guessed.contains('_')) {
                goToResult(true)
            }
        } else {
            mistakes++
            val resId = resources.getIdentifier("hangman_$mistakes", "drawable", packageName)
            binding.imgHangman.setImageResource(resId)
            if (mistakes == maxMistakes) {
                goToResult(false)
            }
        }
    }

    private fun updateWordDisplay() {
        binding.txtWord.text = guessed.joinToString(" ")
    }

    private fun goToResult(won: Boolean) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("won", won)
        startActivity(intent)
        finish()
    }
}
