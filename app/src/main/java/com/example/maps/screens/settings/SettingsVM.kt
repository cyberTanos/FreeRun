package com.example.maps.screens.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.maps.R
import com.example.maps.data.preference.Preferences
import com.example.maps.model.data.User
import com.example.maps.other.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsVM @Inject constructor(
    private val resProvider: ResourceProvider,
    private val pref: Preferences
) : ViewModel() {

    val error = MutableLiveData<String>()

    fun getName(): String {
        return pref.getUserName()
    }

    fun getWeight(): String {
        return pref.getUserWeight().toString()
    }

    fun applyChanges(name: String, weight: String): Boolean {
        if (name.isEmpty() || weight.isEmpty()) {
            error.value = resProvider.getString(R.string.error_empty_text_field)
            return false
        }
        val user = User(name = name, weight = weight, isSave = true)
        pref.saveUser(user)
        return true
    }
}
