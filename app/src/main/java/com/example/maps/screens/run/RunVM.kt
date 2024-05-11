package com.example.maps.screens.run

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
class RunVM @Inject constructor(
    private val repository: MainRepository,
    private val sharedPreferences: Preferences
) : ViewModel() {

    val runsSortedBy = MutableLiveData<List<RunEntity>>()

    fun sortByDate() {
        viewModelScope.launch {
            runsSortedBy.value = repository.getAllRunSortedByDate()
        }
    }

    fun sortByDistance() {
        viewModelScope.launch {
            runsSortedBy.value = repository.getAllRunSortedByDistance()
        }
    }

    fun sortByCaloriesBurned() {
        viewModelScope.launch {
            runsSortedBy.value = repository.getAllRunSortedByCaloriesBurned()
        }
    }

    fun sortByTimeInMillis() {
        viewModelScope.launch {
            runsSortedBy.value = repository.getAllRunSortedByTimeInMillis()
        }
    }

    fun sortByAvgSpeed() {
        viewModelScope.launch {
            runsSortedBy.value = repository.getAllRunSortedByAvgSpeed()
        }
    }

    fun getNameUserForToolbar(): String {
        return sharedPreferences.getUserName()
    }
}
