package com.ramon.iecamobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    val emailInput = findViewById<TextView>(R.id.email_input)
    val passwordInput = findViewById<TextView>(R.id.password_input)
    val loginButton = findViewById<Button>(R.id.login_button)
    val forgotPassword = findViewById<Button>(R.id.forgot_password_button)
    val redirect = findViewById<Button>(R.id.register_button)
    val auth = FirebaseAuth.getInstance()

    loginButton.setOnClickListener {
      val email = emailInput.text.toString()
      val password = passwordInput.text.toString()

      auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(this) { task ->
          if (task.isSuccessful) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
          } else {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
          }
        }
    }

    forgotPassword.setOnClickListener {
      val intent = Intent(this, ForgotPasswordActivity::class.java)
      startActivity(intent)
    }

    redirect.setOnClickListener {
      val intent = Intent(this, RegisterActivity::class.java)
      startActivity(intent)
    }
  }
}