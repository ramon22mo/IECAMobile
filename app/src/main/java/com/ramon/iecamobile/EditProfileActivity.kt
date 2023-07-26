package com.ramon.iecamobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase

class EditProfileActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_edit_profile)

    val fullNameProfile = findViewById<TextView>(R.id.full_name_profile)
    val fullName = findViewById<EditText>(R.id.full_name)
    val email = findViewById<EditText>(R.id.email)
    val password = findViewById<EditText>(R.id.password)
    val updateButton = findViewById<Button>(R.id.update_button)
    val backButton = findViewById<ImageView>(R.id.back_button)
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val database = FirebaseDatabase.getInstance()
    val reference = database.getReference("users").child(user!!.uid)

    reference.get().addOnSuccessListener { dataSnapshot ->
      val fullNameValue = dataSnapshot.child("fullName").value.toString()
      val emailValue = dataSnapshot.child("email").value.toString()
      val passwordValue = dataSnapshot.child("password").value.toString()

      fullNameProfile.text = fullNameValue
      fullName.setText(fullNameValue)
      email.setText(emailValue)
      password.setText(passwordValue)
    }

    updateButton.setOnClickListener {
      val updateName = fullName.text.toString().trim()
      val updateEmail = email.text.toString().trim()
      val updatePassword = password.text.toString().trim()

      if (updateName.isEmpty() || updateEmail.isEmpty() || updatePassword.isEmpty()) {
        Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
      } else {
        if (updateName != fullNameProfile.text.toString()) {
          user.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(updateName).build())
          reference.child("fullName").setValue(updateName)
          Toast.makeText(this, "Full name updated", Toast.LENGTH_SHORT).show()
        } else if (updateEmail != user.email) {
          user.updateEmail(updateEmail)
            .addOnSuccessListener {
              reference.child("email").setValue(updateEmail)
              Toast.makeText(this, "Email updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
              Toast.makeText(this, "Failed to update email: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else if (updatePassword.isNotEmpty()) {
          user.updatePassword(updatePassword)
            .addOnSuccessListener {
              reference.child("password").setValue(updatePassword)
              Toast.makeText(this, "Password updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
              Toast.makeText(this, "Failed to update password: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
          Toast.makeText(this, "Nothing to update", Toast.LENGTH_SHORT).show()
        }
      }
    }

    backButton.setOnClickListener {
      startActivity(Intent(this, HomeActivity::class.java))
    }
  }
}