package com.ramon.iecamobile

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ramon.iecamobile.ui.theme.IECAMobileTheme

class MainActivity : ComponentActivity() {
  private lateinit var topAnim: Animation
  private lateinit var bottomAnim: Animation
  private lateinit var logo: ImageView
  private lateinit var title: TextView
  private var splashScreen = 5000

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
    bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
    logo = findViewById(R.id.logo)
    title = findViewById(R.id.title)

    logo.animation = topAnim
    title.animation = bottomAnim

    topAnim.setAnimationListener(object : Animation.AnimationListener {
      override fun onAnimationStart(animation: Animation?) {}
      override fun onAnimationEnd(animation: Animation?) {
        logo.clearAnimation()
        title.clearAnimation()
      }
      override fun onAnimationRepeat(animation: Animation?) {}
    })

    Handler().postDelayed({
      startActivity(Intent(this, LoginActivity::class.java))
      finish()
    }, splashScreen.toLong())
  }
}