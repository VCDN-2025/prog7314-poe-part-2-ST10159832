package student.projects.wordtopia2.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {

    fun applyTheme(context: Context) {
        val prefs = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val isDarkMode = prefs.getBoolean("dark_mode", false)
        val textSize = prefs.getString("text_size", "Medium")

        // Apply theme
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode)
                AppCompatDelegate.MODE_NIGHT_YES
            else
                AppCompatDelegate.MODE_NIGHT_NO
        )

        // Apply text scaling globally
        val config = context.resources.configuration
        val scale = when (textSize) {
            "Small" -> 0.85f
            "Large" -> 1.2f
            else -> 1.0f
        }
        config.fontScale = scale
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}
