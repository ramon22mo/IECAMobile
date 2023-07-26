package com.ramon.iecamobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ramon.iecamobile.adapters.cards.CardAdapter
import com.ramon.iecamobile.adapters.cards.CardModel
import com.ramon.iecamobile.adapters.explorer.ExplorerAdapter
import com.ramon.iecamobile.adapters.explorer.ExplorerModel

class CourseActivity : AppCompatActivity() {
  private lateinit var adapter: CardAdapter
  private lateinit var recyclerView: RecyclerView
  private lateinit var database: FirebaseDatabase

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_course)

    val redirect = findViewById<ImageView>(R.id.iv_back)

    recyclerView = findViewById(R.id.rv_course)
    recyclerView.layoutManager = LinearLayoutManager(this)

    adapter = CardAdapter(this, mutableListOf())
    recyclerView.adapter = adapter
    database = FirebaseDatabase.getInstance()

    val coursesRef = database.getReference("courses")
    coursesRef.addValueEventListener(object : ValueEventListener {
      override fun onDataChange(snapshot: DataSnapshot) {
        adapter.clear()

        val coursesList = mutableListOf<CardModel>()
        for (courseSnapshot in snapshot.children) {
          val title = courseSnapshot.child("title").getValue(String::class.java)
          val status = courseSnapshot.child("status").getValue(Boolean::class.java)
          if (title != null && status != null) {
            val course = CardModel(title, status)
            coursesList.add(course)
          }
        }
        adapter.setData(coursesList)
      }

      override fun onCancelled(error: DatabaseError) {
        Toast.makeText(this@CourseActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
      }
    })

    redirect.setOnClickListener {
      startActivity(Intent(this, HomeActivity::class.java))
    }
  }
}