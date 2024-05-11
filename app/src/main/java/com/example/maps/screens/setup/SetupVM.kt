package com.example.maps.screens.setup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.maps.data.preference.Preferences
import com.example.maps.model.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetupVM @Inject constructor(
    private val sharedPreferences: Preferences
) : ViewModel() {

    val error = MutableLiveData<String>()

    fun checkSaveUser(name: String, weight: String): Boolean {
        if (name.isEmpty() || weight.isEmpty()) {
            error.value = "Пустое поле"
            return false
        }
        val user = User(name = name, weight = weight, isSave = true)
        sharedPreferences.saveUser(user)
        return true
    }

    fun getSavedUser(): Boolean {
        return sharedPreferences.getUserIsSave()
    }
}
