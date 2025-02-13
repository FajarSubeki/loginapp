package id.loginapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import id.loginapp.R
import id.loginapp.utils.PreferenceManager

class LoginActivity : AppCompatActivity() {

    private lateinit var prefs: PreferenceManager
    private lateinit var etUsername: EditText // Declare globally
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        prefs = PreferenceManager(this)

        if (prefs.isLoggedIn()) {
            redirectActivity()
        } else {
            initView()
        }

    }

    private fun initView() {
        // Initialize views
        prefs = PreferenceManager(this)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                prefs.saveLogin(username, password)
                redirectActivity()
            } else {
                Toast.makeText(applicationContext, "Username atau password kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun redirectActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}