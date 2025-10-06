import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import student.projects.wordtopia2.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val languages = listOf("English", "Zulu", "Afrikaans", "French")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerLanguage.adapter = adapter


        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, if (isChecked) "Dark Mode Enabled" else "Light Mode Enabled", Toast.LENGTH_SHORT).show()
        }


        binding.radioGroupTextSize.setOnCheckedChangeListener { _, checkedId ->
            val size = when (checkedId) {
               // R.id.radioSmall -> "Small"
                //R.id.radioMedium -> "Medium"
                //R.id.radioLarge -> "Large"
                else -> "Medium"
            }
            Toast.makeText(this, "Text size: $size", Toast.LENGTH_SHORT).show()
        }

        binding.btnBackSettings.setOnClickListener {
            finish()
        }
    }
}