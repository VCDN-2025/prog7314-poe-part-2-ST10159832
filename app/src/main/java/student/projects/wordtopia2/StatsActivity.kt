package student.projects.wordtopia2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import student.projects.wordtopia2.databinding.ActivityStatsBinding

class StatsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStatsBinding
    private var games = 0
    private var wins = 0
    private var losses = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateStats()

        binding.btnReset.setOnClickListener {
            games = 0
            wins = 0
            losses = 0
            updateStats()
        }
    }

    private fun updateStats() {
        binding.txtGames.text = "Games Played: $games"
        binding.txtWins.text = "Wins: $wins"
        binding.txtLosses.text = "Losses: $losses"
    }
}
