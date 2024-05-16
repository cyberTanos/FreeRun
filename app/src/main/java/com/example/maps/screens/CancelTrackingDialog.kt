package com.example.maps.screens

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.R.style
import com.example.maps.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CancelTrackingDialog : DialogFragment() {

    private var yesListener: (() -> Unit)? = null

    fun setYesListener(listener: () -> Unit) {
        yesListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext(), style.AlertDialog_AppCompat)
            .setTitle(getString(R.string.tracking_dialog_title))
            .setMessage(getString(R.string.tracking_dialog_message))
            .setIcon(R.drawable.ic_delete)
            .setPositiveButton(getString(R.string.tracking_dialog_positive_bt)) { _, _ ->
                yesListener?.let { yes ->
                    yes()
                }
            }
            .setNegativeButton(getString(R.string.tracking_dialog_negative_bt)) { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .create()
    }
}
