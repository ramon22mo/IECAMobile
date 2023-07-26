package com.ramon.iecamobile.adapters.cards

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.ramon.iecamobile.R

class CardAdapter(private val context: Context, private var data: MutableList<CardModel>) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(context).inflate(R.layout.cards_item, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = data[position]
    holder.bind(item)
  }

  override fun getItemCount(): Int {
    return data.size
  }

  fun clear() {
    data.clear()
    notifyDataSetChanged()
  }

  fun setData(coursesList: MutableList<CardModel>) {
    (data as MutableList).addAll(coursesList)
    notifyDataSetChanged()
  }


  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val courseTitle: TextView = itemView.findViewById(R.id.course_title)
    private val courseStatus: TextView = itemView.findViewById(R.id.course_status)
    private val courseCard: MaterialCardView = itemView.findViewById(R.id.course_card)

    fun bind(item: CardModel) {
      courseTitle.text = item.title
      courseStatus.text = item.status.toString()

      if (item.status) {
        courseStatus.setTextColor(context.getColor(R.color.green))
        courseCard.strokeColor = context.getColor(R.color.green)
        courseStatus.text = "Completada"
      } else {
        courseStatus.setTextColor(context.getColor(R.color.red))
        courseCard.strokeColor = context.getColor(R.color.red)
        courseStatus.text = "Pendiente"
      }
    }
  }
}

