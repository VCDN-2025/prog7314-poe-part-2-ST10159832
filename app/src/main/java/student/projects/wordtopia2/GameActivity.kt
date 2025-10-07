package student.projects.wordtopia2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import student.projects.wordtopia2.api.RetrofitClient
import student.projects.wordtopia2.api.ScoreRequest
import student.projects.wordtopia2.databinding.ActivityGameBinding
import student.projects.wordtopia2.utils.ThemeManager
import kotlin.random.Random

class GameActivity : BaseActivity() {
    private lateinit var binding: ActivityGameBinding

    private lateinit var selectedWord: String
    private lateinit var guessed: CharArray
    private var mistakes = 0
    private val maxMistakes = 6
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeManager.applyTheme(this)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔤 Load preferred language
        val prefs = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val language = prefs.getString("language", "en")

        // 🧠 Choose word list based on selected language
        val wordList = when (language) {
            "zu" -> listOf("UDINGA", "ISIKOLE", "UMSEBENZI", "INJA", "IKHAYA") // Zulu words
            "af" -> listOf("MUUR", "REKENAAR", "TAAL", "STADIG", "ONTWIKKEL") // Afrikaans words
            "fr" -> listOf("MUR", "ANDROID", "LANGAGE", "PUZZLE", "BOUTON") // French words
            else -> listOf("WALL", "ANDROID", "KOTLIN", "RETROFIT", "PUZZLE",
                "DEVELOPER", "LANGUAGE", "MOBILE", "STUDIO", "BUTTON")
        }

        // ✅ Randomly select a word
        selectedWord = wordList.random().uppercase()
        guessed = CharArray(selectedWord.length) { '_' }

        // 👋 Notify user which language they’re playing in
        Toast.makeText(this, "Playing in ${language?.uppercase()}", Toast.LENGTH_SHORT).show()

        setupKeyboard()
        updateWordDisplay()
    }

    // 🧩 Create keyboard dynamically (A–Z)
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

    // 🎯 Handle player guesses
    private fun handleGuess(letter: Char, btn: Button) {
        btn.isEnabled = false

        if (selectedWord.contains(letter)) {
            for (i in selectedWord.indices) {
                if (selectedWord[i] == letter) guessed[i] = letter
            }
            updateWordDisplay()

            // ✅ Win condition
            if (!guessed.contains('_')) {
                calculateScore()
                updateGameStats(true)
                submitScoreToApi()
                goToResult(true)
            }
        } else {
            mistakes++
            val resId = resources.getIdentifier("hangman_$mistakes", "drawable", packageName)
            binding.imgHangman.setImageResource(resId)

            // ❌ Lose condition
            if (mistakes == maxMistakes) {
                calculateScore()
                updateGameStats(false)
                submitScoreToApi()
                goToResult(false)
            }
        }
    }

    // 📝 Update displayed word
    private fun updateWordDisplay() {
        binding.txtWord.text = guessed.joinToString(" ")
    }

    // 🧮 Score calculation
    private fun calculateScore() {
        score = (selectedWord.length * 10) - (mistakes * 5)
        if (score < 0) score = 0
    }

    // 🚪 Go to result screen
    private fun goToResult(won: Boolean) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("won", won)
        intent.putExtra("score", score)
        intent.putExtra("word", selectedWord)
        startActivity(intent)
        finish()
    }

    // 📊 Track stats locally
    private fun updateGameStats(isWin: Boolean) {
        val prefs = getSharedPreferences("UserStats", Context.MODE_PRIVATE)
        val games = prefs.getInt("games", 0) + 1
        val wins = prefs.getInt("wins", 0) + if (isWin) 1 else 0
        val losses = prefs.getInt("losses", 0) + if (!isWin) 1 else 0

        prefs.edit()
            .putInt("games", games)
            .putInt("wins", wins)
            .putInt("losses", losses)
            .apply()
    }

    // 🌐 Submit score to backend API
    private fun submitScoreToApi() {
        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val username = prefs.getString("username", "Guest") ?: "Guest"

        val request = ScoreRequest(username, score)
        RetrofitClient.instance.submitScore(request).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (!response.isSuccessful) {
                    Toast.makeText(
                        this@GameActivity,
                        "Score submission failed: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(
                    this@GameActivity,
                    "Error submitting score: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
