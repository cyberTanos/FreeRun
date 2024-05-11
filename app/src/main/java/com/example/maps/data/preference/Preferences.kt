package com.example.maps.data.preference

import android.content.SharedPreferences
import com.example.maps.model.data.User
import com.example.maps.other.KEY_FIRST_TIME_TOGGLE
import com.example.maps.other.KEY_NAME
import com.example.maps.other.KEY_WEIGHT

class Preferences(private val sharedPreferences: SharedPreferences) {

    fun saveUser(user: User) {
        sharedPreferences.edit()
            .putString(KEY_NAME, user.name)
            .putFloat(KEY_WEIGHT, user.weight.toFloat())
            .putBoolean(KEY_FIRST_TIME_TOGGLE, user.isSave)
            .apply()
    }

    fun getUserName(): String {
        return sharedPreferences.getString(KEY_NAME, "") ?: ""
    }

    fun getUserWeight(): Float {
        return sharedPreferences.getFloat(KEY_WEIGHT, 80f)
    }

    fun getUserIsSave(): Boolean {
        return sharedPreferences.getBoolean(KEY_FIRST_TIME_TOGGLE, false)
    }
}
