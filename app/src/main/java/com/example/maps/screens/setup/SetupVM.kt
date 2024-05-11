package com.example.maps.screens.setup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.maps.R
import com.example.maps.data.preference.Preferences
import com.example.maps.model.data.User
import com.example.maps.other.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetupVM @Inject constructor(
    private val pref: Preferences,
    private val resProvider: ResourceProvider
) : ViewModel() {

    val error = MutableLiveData<String>()

    fun checkSaveUser(name: String, weight: String): Boolean {
        if (name.isEmpty() || weight.isEmpty()) {
            error.value = resProvider.getString(R.string.error_empty_text_field)
            return false
        }
        val user = User(name = name, weight = weight, isSave = true)
        pref.saveUser(user)
        return true
    }

    fun getSavedUser(): Boolean {
        return pref.getUserIsSave()
    }
}
