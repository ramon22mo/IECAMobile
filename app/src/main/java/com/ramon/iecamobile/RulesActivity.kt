package com.ramon.iecamobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class RulesActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_rules)

    val redirect = findViewById<ImageView>(R.id.iv_back)

    redirect.setOnClickListener {
      startActivity(Intent(this, HomeActivity::class.java))
    }
  }
}