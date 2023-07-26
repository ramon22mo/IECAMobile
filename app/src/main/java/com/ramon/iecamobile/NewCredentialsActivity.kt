package com.ramon.iecamobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NewCredentialsActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_new_credentials)

    val passwordInput = findViewById<EditText>(R.id.password_input)
    val confirmPasswordInput = findViewById<EditText>(R.id.password_confirm_input)
    val updateButton = findViewById<Button>(R.id.update_button)
    val database = FirebaseDatabase.getInstance()

    updateButton.setOnClickListener {
      val password = passwordInput.text.toString()
      val confirmPassword = confirmPasswordInput.text.toString()
      val auth = FirebaseAuth.getInstance()
      val user = auth.currentUser

      if (password != confirmPassword) {
        Toast.makeText(this, "The passwords do not match.", Toast.LENGTH_SHORT).show()
      } else if (user != null) {
        val uid = user.uid
        val reference = database.getReference("users").child(uid).child("password")

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
          override fun onDataChange(snapshot: DataSnapshot) {
            val currentPassword = snapshot.getValue(String::class.java)

            if (password == currentPassword) {
              Toast.makeText(this@NewCredentialsActivity, "The new password cannot be the same as the old one.", Toast.LENGTH_SHORT).show()
            } else {
              reference.setValue(password)
              user.updatePassword(password)
                .addOnCompleteListener { task ->
                  if (task.isSuccessful) {
                    Toast.makeText(this@NewCredentialsActivity, "Password updated", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@NewCredentialsActivity, LoginActivity::class.java))
                  } else {
                    Toast.makeText(this@NewCredentialsActivity, "Error updating password.", Toast.LENGTH_SHORT).show()
                  }
                }
            }
          }
          override fun onCancelled(error: DatabaseError) {
            Toast.makeText(this@NewCredentialsActivity, "Error accessing the database", Toast.LENGTH_SHORT).show()
          }
        })
      } else {
        Toast.makeText(this@NewCredentialsActivity, "The user is not authenticated", Toast.LENGTH_SHORT).show()
      }
    }
  }
}