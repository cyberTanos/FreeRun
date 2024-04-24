package com.example.maps.screens.run

import androidx.lifecycle.ViewModel
import com.example.maps.domain.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RunVM @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

}
