package com.example.maps.screens.statistics

import androidx.lifecycle.ViewModel
import com.example.maps.domain.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsVM @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

}
