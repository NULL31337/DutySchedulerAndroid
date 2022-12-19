package com.github.null31337.dutyscheduler.duty

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.null31337.dutyscheduler.R
import com.github.null31337.dutyscheduler.model.DutyReceive
import com.github.null31337.dutyscheduler.model.DutyStatus
import com.github.null31337.dutyscheduler.model.DutyStatus.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.min

class DutyAdapter : ListAdapter<DutyReceive, DutyAdapter.DutyViewHolder>(DiffCallback()) {

  class DutyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(dutyReceive: DutyReceive) {
      with(itemView) {
        findViewById<TextView>(R.id.name).apply {
          text = dutyReceive.name
          setTextColor(0xFF000000.toInt())
        }
        findViewById<TextView>(R.id.description).apply {
          text = dutyReceive.description
          setTextColor(0xFF000000.toInt())
        }
        findViewById<TextView>(R.id.deadline).apply {
          text = dutyReceive.deadline
          setTextColor(0xFF000000.toInt())
        }

        findViewById<TextView>(R.id.status).apply {
          text = dutyReceive.status.toString()
          setTextColor(0xFF000000.toInt())
        }

        val days =
          (SimpleDateFormat("dd.MM.yyyy").parse(dutyReceive.deadline).time - Date().time) / (1000 * 60 * 60 * 24)
        val hex = if (days < 0) 20 else min(170, days.toInt() * 50)
        findViewById<CardView>(R.id.card).setCardBackgroundColor(
          when (dutyReceive.status) {
            IN_PROGRESS -> 0xFF00D2FF.toInt() + hex * 16 * 16 * 16 * 16
            DONE -> 0xFF00FF71.toInt() + hex / 2 + hex * 16 * 16 * 16 * 16
            TODO -> 0xFFFFDB00.toInt() + hex
          }
        )
      }

    }
  }

  private class DiffCallback : DiffUtil.ItemCallback<DutyReceive>() {
    override fun areItemsTheSame(oldItem: DutyReceive, newItem: DutyReceive): Boolean {
      return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DutyReceive, newItem: DutyReceive): Boolean {
      return oldItem == newItem
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DutyViewHolder =
    DutyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_duty, parent, false))

  override fun onBindViewHolder(holder: DutyViewHolder, position: Int) {
    holder.bind(currentList[position])
  }
}