package student.projects.wordtopia2

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import student.projects.wordtopia2.databinding.ActivityStatsBinding
import student.projects.wordtopia2.utils.ThemeManager


class StatsActivity : BaseActivity() {
    private lateinit var binding: ActivityStatsBinding
    private var games = 0
    private var wins = 0
    private var losses = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeManager.applyTheme(this)
        binding = ActivityStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("UserStats", Context.MODE_PRIVATE)
        var games = prefs.getInt("games", 0)
        var wins = prefs.getInt("wins", 0)
        var losses = prefs.getInt("losses", 0)

        updateStats(games, wins, losses)

        // Reset button clears stats
        binding.btnReset.setOnClickListener {
            games = 0
            wins = 0
            losses = 0
            prefs.edit().putInt("games", 0)
                .putInt("wins", 0)
                .putInt("losses", 0)
                .apply()
            updateStats(games, wins, losses)
        }
    }

    private fun updateStats(games: Int, wins: Int, losses: Int) {
        binding.txtGames.text = "Games Played: $games"
        binding.txtWins.text = "Wins: $wins"
        binding.txtLosses.text = "Losses: $losses"
    }
}