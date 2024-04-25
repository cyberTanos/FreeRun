package com.example.maps.screens.tracking

import androidx.lifecycle.ViewModel
import com.example.maps.domain.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrackingVM @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

}
