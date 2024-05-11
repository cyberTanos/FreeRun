package com.example.maps.screens.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.data.preference.Preferences
import com.example.maps.domain.MainRepository
import com.example.maps.model.entity.RunEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class StatisticsVM @Inject constructor(
    private val repository: MainRepository,
    private val pref: Preferences
) : ViewModel() {

    val statisticsLD = MutableLiveData<List<RunEntity>>()
    val timeRunLD = MutableLiveData<Long>()
    val distanceLD = MutableLiveData<Int>()
    val caloriesBurnedLD = MutableLiveData<Int>()
    val avgSpeedD = MutableLiveData<Float>()

    fun getTotalTimeRun() {
        viewModelScope.launch {
            timeRunLD.value = repository.getTotalTimeInMillis()
        }
    }

    fun getTotalDistance() {
        viewModelScope.launch {
            distanceLD.value = repository.getTotalDistance()
        }
    }

    fun getTotalCaloriesBurned() {
        viewModelScope.launch {
            caloriesBurnedLD.value = repository.getTotalCaloriesBurned()
        }
    }

    fun getTotalAvgSpeed() {
        viewModelScope.launch {
            avgSpeedD.value = repository.getTotalAvgSpeed()
        }
    }

    fun getSortedByDate() {
        viewModelScope.launch {
            statisticsLD.value = repository.getAllRunSortedByDate()
        }
    }

    fun getName(): String {
        return pref.getUserName()
    }
}
