package com.ramon.iecamobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.ramon.iecamobile.adapters.explorer.ExplorerAdapter
import com.ramon.iecamobile.adapters.explorer.ExplorerModel

class HomeActivity : AppCompatActivity() {
  private lateinit var adapter: ExplorerAdapter
  private lateinit var recyclerView: RecyclerView
  private lateinit var dataList: List<ExplorerModel>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    val fullName = findViewById<TextView>(R.id.full_name)
    val editProfile = findViewById<MaterialCardView>(R.id.edit_profile)
    val logout = findViewById<MaterialCardView>(R.id.logout)
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val database = FirebaseDatabase.getInstance()
    val reference = database.getReference("users").child(user!!.uid)

    recyclerView = findViewById(R.id.recycler_view)
    recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    dataList = createDataList()
    adapter = ExplorerAdapter(this, dataList)
    recyclerView.adapter = adapter

    reference.get().addOnSuccessListener {
      fullName.text = it.child("fullName").value.toString()
    }

    editProfile.setOnClickListener {
      startActivity(Intent(this, EditProfileActivity::class.java))
    }

    logout.setOnClickListener {
      auth.signOut()
      startActivity(Intent(this, LoginActivity::class.java))
      finish()
    }
  }

  private fun createDataList() : List<ExplorerModel> {
    val list = ArrayList<ExplorerModel>()
    list.add(ExplorerModel(R.drawable.course, "CURSO"))
    list.add(ExplorerModel(R.drawable.homework, "TAREA"))
    list.add(ExplorerModel(R.drawable.exam, "EXAMEN"))
    list.add(ExplorerModel(R.drawable.rules, "REGLAMENTO"))

    return list
  }
}