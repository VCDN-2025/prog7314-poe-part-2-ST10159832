package student.projects.wordtopia2

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        // Load user's preferred text size from SharedPreferences
        val prefs = newBase.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val sizePref = prefs.getString("textSize", "Medium")

        // Decide scaling factor for font sizes
        val scale = when (sizePref) {
            "Small" -> 0.85f
            "Medium" -> 1.0f
            "Large" -> 1.15f
            else -> 1.0f
        }

        // Apply scaling to configuration
        val config = Configuration(newBase.resources.configuration)
        config.fontScale = scale

        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }
}
