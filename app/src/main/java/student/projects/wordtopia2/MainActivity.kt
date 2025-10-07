package student.projects.wordtopia2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import student.projects.wordtopia2.databinding.ActivityMainBinding
import student.projects.wordtopia2.utils.ThemeManager

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeManager.applyTheme(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Check if a username is already saved
        val sharedPrefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val savedUsername = sharedPrefs.getString("username", null)

        if (savedUsername == null) {
            // 🚀 Ask user for a username if none is saved
            showUsernameDialog(sharedPrefs)
        } else {
            Toast.makeText(this, "Welcome back, $savedUsername!", Toast.LENGTH_SHORT).show()
        }

        // 🎮 Navigation buttons
        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        binding.btnPlay.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }

        binding.btnGuide.setOnClickListener {
            startActivity(Intent(this, GuideActivity::class.java))
        }

        binding.btnStats.setOnClickListener {
            startActivity(Intent(this, StatsActivity::class.java))
        }
    }

    // 🧩 Show dialog to enter username
    private fun showUsernameDialog(sharedPrefs: android.content.SharedPreferences) {
        val input = EditText(this).apply {
            hint = "Enter your username"
            setPadding(50, 40, 50, 40)
        }

        AlertDialog.Builder(this)
            .setTitle("Welcome to Wordtopia!")
            .setMessage("Please enter your username to get started:")
            .setView(input)
            .setCancelable(false)
            .setPositiveButton("Save") { _, _ ->
                val username = input.text.toString().trim()
                if (username.isEmpty()) {
                    Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
                    showUsernameDialog(sharedPrefs) // ask again if empty
                } else {
                    sharedPrefs.edit().putString("username", username).apply()
                    Toast.makeText(this, "Welcome, $username!", Toast.LENGTH_SHORT).show()
                }
            }
            .show()
    }
}