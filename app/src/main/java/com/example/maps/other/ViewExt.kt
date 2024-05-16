package com.example.maps.other

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner

fun Spinner.setListener(action: (position: Int) -> Unit) {
    this.onItemSelectedListener = object : OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            action.invoke(position)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) = Unit
    }
}
