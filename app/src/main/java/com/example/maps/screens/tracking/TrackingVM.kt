package com.example.maps.screens.tracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.domain.MainRepository
import com.example.maps.model.entity.RunEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TrackingVM @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    fun insertRun(runEntity: RunEntity) {
        viewModelScope.launch {
            repository.insertRun(runEntity)
        }
    }
}
