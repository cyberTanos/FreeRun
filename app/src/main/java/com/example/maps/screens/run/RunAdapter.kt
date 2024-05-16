package com.example.maps.screens.run

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.maps.R
import com.example.maps.databinding.ItemRunBinding
import com.example.maps.model.entity.RunEntity
import com.example.maps.other.TrackingUtility
import com.example.maps.screens.run.RunAdapter.RunVH
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val METERS_IN_KM = 1000f

class RunAdapter : ListAdapter<RunEntity, RunVH>(Differ) {

    inner class RunVH(private val binding: ItemRunBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(runEntity: RunEntity) {
            Glide.with(binding.root).load(runEntity.image).into(binding.ivRunImage)
            val calendar = Calendar.getInstance().apply {
                timeInMillis = runEntity.timeInMillis
            }
            val dateFormat = SimpleDateFormat(binding.root.context.getString(R.string.data_format), Locale.getDefault())
            binding.tvDate.text = dateFormat.format(calendar.time)

            val avgSpeed = "${runEntity.avgSpeedInKMH}${binding.root.context.getString(R.string.avg_speed_kmh)}"
            binding.tvAvgSpeed.text = avgSpeed

            val distanceInKm = "${runEntity.distanceInMeters / METERS_IN_KM}${binding.root.context.getString(R.string.distance_km)}"
            binding.tvDistance.text = distanceInKm

            binding.tvTime.text = TrackingUtility.getFormattedStopWatchTime(runEntity.timeInMillis)

            val caloriesBurned = "${runEntity.caloriesBurned}${binding.root.context.getString(R.string.calories_kcal)}"
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