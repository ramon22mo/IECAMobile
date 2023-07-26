package com.ramon.iecamobile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class RegisterActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_register)

    val registerFullName = findViewById<EditText>(R.id.register_full_name)
    val registerEmail = findViewById<EditText>(R.id.register_email)
    val registerPassword = findViewById<EditText>(R.id.register_password)
    val registerConfirmPassword = findViewById<EditText>(R.id.register_confirm_password)
    val registerButton = findViewById<Button>(R.id.register_button)
    val redirect = findViewById<Button>(R.id.already_account)
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    var reference: DatabaseReference

    registerButton.setOnClickListener {
      val email = registerEmail.text.toString()
      val password = registerPassword.text.toString()
      val confirmPassword = registerConfirmPassword.text.toString()

      if (email.isEmpty() || password.isEmpty()) {
        Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }

      if (password != confirmPassword) {
        Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }

      auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
        if (task.isSuccessful) {
          val fullName = registerFullName.text.toString()
          val uid = task.result.user?.uid
          val userMap = HashMap<String, Any>()
          userMap["fullName"] = fullName
          userMap["email"] = email
          userMap["password"] = password
          userMap["uid"] = uid!!

          reference = database.getReference("users")
          reference.child(uid).setValue(userMap).addOnCompleteListener {
            if (it.isSuccessful) {
              Toast.makeText(this, "Success registering user", Toast.LENGTH_SHORT).show()
              startActivity(Intent(this, LoginActivity::class.java))
            } else {
              Toast.makeText(this, "Error registering user: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
            }
          }
        } else {
          Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
        }
      }
    }

    redirect.setOnClickListener {
      val intent = Intent(this, LoginActivity::class.java)
      startActivity(intent)
    }
  }
}
