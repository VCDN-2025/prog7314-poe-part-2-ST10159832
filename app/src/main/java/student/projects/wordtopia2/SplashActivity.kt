package student.projects.wordtopia2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import student.projects.wordtopia2.utils.ThemeManager

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeManager.applyTheme(this)
        setContentView(R.layout.activity_splash)


        // Find the buttons
        val loginBtn: Button = findViewById(R.id.btnLoginSplash)
        val signupBtn: Button = findViewById(R.id.btnSignUpSplash)

        // Navigate to LoginActivity
        loginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Navigate to SignUpActivity
        signupBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}