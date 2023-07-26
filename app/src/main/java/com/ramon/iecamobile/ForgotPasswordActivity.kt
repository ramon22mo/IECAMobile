package com.ramon.iecamobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ForgotPasswordActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_forgot_password)

    val emailInput = findViewById<EditText>(R.id.email_input)
    val sendButton = findViewById<Button>(R.id.send_button)
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()

    sendButton.setOnClickListener {
      val email = emailInput.text.toString()
      val user = auth.currentUser

      if (user != null) {
        val uid = user.uid
        val reference = database.getReference("users").child(uid).child("email")

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
          override fun onDataChange(snapshot: DataSnapshot) {
            val emailDatabase = snapshot.getValue(String::class.java)

            if (email == emailDatabase) {
              startActivity(Intent(this@ForgotPasswordActivity, NewCredentialsActivity::class.java))
            } else {
              Toast.makeText(this@ForgotPasswordActivity, "Email not found.", Toast.LENGTH_SHORT).show()
            }
          }

          override fun onCancelled(error: DatabaseError) {
            Toast.makeText(this@ForgotPasswordActivity, "Error accessing the database.", Toast.LENGTH_SHORT).show()
          }
        })
      } else {
        Toast.makeText(this@ForgotPasswordActivity, "The user is not authenticated.", Toast.LENGTH_SHORT).show()
      }
    }
  }
}