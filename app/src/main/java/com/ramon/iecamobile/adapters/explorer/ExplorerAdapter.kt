package com.ramon.iecamobile.adapters.explorer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ramon.iecamobile.CourseActivity
import com.ramon.iecamobile.ExamActivity
import com.ramon.iecamobile.R
import com.ramon.iecamobile.RulesActivity

class ExplorerAdapter(private val context: Context, private var data: List<ExplorerModel>) : RecyclerView.Adapter<ExplorerAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(context).inflate(R.layout.carousel_item, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = data[position]
    holder.bind(item)

    holder.itemView.setOnClickListener {
      when (position) {
        0 -> {
          val intent = Intent(context, CourseActivity::class.java)
          context.startActivity(intent)
        }
        1 -> {
          val intent = Intent(context, ExamActivity::class.java)
          context.startActivity(intent)
        }
        2 -> {
          val intent = Intent(context, ExamActivity::class.java)
          context.startActivity(intent)
        }
        3 -> {
          val intent = Intent(context, RulesActivity::class.java)
          context.startActivity(intent)
        }
      }
    }
  }

  override fun getItemCount(): Int {
    return data.size
  }


  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val explorerTitle: TextView = itemView.findViewById(R.id.title_explorer)
    private val explorerImage: ImageView = itemView.findViewById(R.id.image_explorer)

    fun bind(item: ExplorerModel) {
      explorerTitle.text = item.title
      explorerImage.setImageResource(item.image)
    }
  }
}

