package com.example.maps.screens.run

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.maps.databinding.ItemRunBinding
import com.example.maps.model.entity.RunEntity
import com.example.maps.other.TrackingUtility
import com.example.maps.screens.run.RunAdapter.RunVH
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RunAdapter : ListAdapter<RunEntity, RunVH>(Differ) {

    inner class RunVH(private val binding: ItemRunBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(runEntity: RunEntity) {
            Glide.with(binding.root).load(runEntity.image).into(binding.ivRunImage)
            val calendar = Calendar.getInstance().apply {
                timeInMillis = runEntity.timeInMillis
            }
            val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            binding.tvDate.text = dateFormat.format(calendar.time)

            val avgSpeed = "${runEntity.avgSpeedInKMH}km/h"
            binding.tvAvgSpeed.text = avgSpeed

            val distanceInKm = "${runEntity.distanceInMeters / 1000f}km"
            binding.tvDistance.text = distanceInKm

            binding.tvTime.text = TrackingUtility.getFormattedStopWatchTime(runEntity.timeInMillis)

            val caloriesBurned = "${runEntity.caloriesBurned}kcal"
            binding.tvCalories.text = caloriesBurned
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRunBinding.inflate(inflater, parent, false)
        return RunVH(binding)
    }

    override fun onBindViewHolder(holder: RunVH, position: Int) {
        holder.bind(getItem(position))
    }

    object Differ : DiffUtil.ItemCallback<RunEntity>() {

        override fun areItemsTheSame(oldItem: RunEntity, newItem: RunEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RunEntity, newItem: RunEntity): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
}