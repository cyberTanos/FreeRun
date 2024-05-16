package com.example.maps.screens.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.maps.data.preference.Preferences
import com.example.maps.domain.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsVM @Inject constructor(
    private val repository: MainRepository,
    private val pref: Preferences
) : ViewModel() {

    val statisticsLD = liveData {
        emit(repository.getAllRunSortedByDate())
    }
    val avgSpeedD = liveData {
        emit(repository.getTotalAvgSpeed())
    }

    val timeRunLD = liveData {
        emit(repository.getTotalTimeInMillis())
    }
    val distanceLD = liveData {
        emit(repository.getTotalDistance())
    }
    val caloriesBurnedLD = liveData {
        emit(repository.getTotalCaloriesBurned())
    }

    fun getName(): String {
        return pref.getUserName()
    }
}
