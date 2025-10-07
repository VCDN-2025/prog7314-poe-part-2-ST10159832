package student.projects.wordtopia2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import student.projects.wordtopia2.databinding.ActivitySettingsBinding
import student.projects.wordtopia2.utils.ThemeManager
import java.util.Locale

class SettingsActivity : BaseActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) { // 👈 Apply saved theme/text size first
        super.onCreate(savedInstanceState)
        ThemeManager.applyTheme(this)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)

        // --- Language Spinner ---
        val languages = listOf("English", "Zulu", "Afrikaans", "French")
        val languageCodes = mapOf(
            "English" to "en",
            "Zulu" to "zu",
            "Afrikaans" to "af",
            "French" to "fr"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerLanguage.adapter = adapter

        val savedLanguage = prefs.getString("language", "en")
        val selectedIndex = languages.indexOfFirst { languageCodes[it] == savedLanguage }
        if (selectedIndex >= 0) binding.spinnerLanguage.setSelection(selectedIndex)

        binding.spinnerLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedLanguage = languages[position]
                val langCode = languageCodes[selectedLanguage] ?: "en"

                prefs.edit().putString("language", langCode).apply()

                val locale = Locale(langCode)
                Locale.setDefault(locale)
                val config = resources.configuration
                config.setLocale(locale)
                resources.updateConfiguration(config, resources.displayMetrics)

                Toast.makeText(applicationContext, "Language set to $selectedLanguage", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // --- Dark Mode Switch ---
        val isDark = prefs.getBoolean("dark_mode", false)
        binding.switchTheme.isChecked = isDark

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("dark_mode", isChecked).apply()
            ThemeManager.applyTheme(this)
            recreate() // reload activity to apply changes
        }

        // --- Text Size Radio Buttons ---
        val savedSize = prefs.getString("text_size", "Medium")
        when (savedSize) {
            "Small" -> binding.radioSmall.isChecked = true
            "Medium" -> binding.radioMedium.isChecked = true
            "Large" -> binding.radioLarge.isChecked = true
        }

        binding.radioGroupTextSize.setOnCheckedChangeListener { _, checkedId ->
            val size = when (checkedId) {
                R.id.radioSmall -> "Small"
                R.id.radioMedium -> "Medium"
                R.id.radioLarge -> "Large"
                else -> "Medium"
            }

            prefs.edit().putString("text_size", size).apply()
            ThemeManager.applyTheme(this)
            recreate() // reload to apply immediately
            Toast.makeText(this, "Text size: $size", Toast.LENGTH_SHORT).show()
        }

        // --- Back Button ---
        binding.btnBackSettings.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        binding.btnLogout.setOnClickListener {
            // Sign out the user from Firebase
            FirebaseAuth.getInstance().signOut()

            // Go back to LoginActivity (or wherever your login screen is)
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish() // close SettingsActivity
        }
    }
}
