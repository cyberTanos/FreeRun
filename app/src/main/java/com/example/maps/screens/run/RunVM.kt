package com.example.maps.screens.run

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.domain.MainRepository
import com.example.maps.model.entity.RunEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RunVM @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    val runsSortedByDate = MutableLiveData<List<RunEntity>>()

    fun sortedByDate() {
        viewModelScope.launch {
            val rep = repository.getAllRunSortedByDate()
            runsSortedByDate.value = rep
        }
    }
}
