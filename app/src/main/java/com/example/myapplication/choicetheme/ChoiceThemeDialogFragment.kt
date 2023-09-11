package com.example.myapplication.choicetheme

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.myapplication.MainActivity.Companion.KEY_FOR_DARK
import com.example.myapplication.MainActivity.Companion.KEY_FOR_LIGHT
import com.example.myapplication.MainActivity.Companion.KEY_FOR_PRESSED_BUTTON
import com.example.myapplication.MainActivity.Companion.KEY_FOR_SYSTEM
import com.example.myapplication.MainActivity.Companion.REQUEST_KEY_FOR_DIALOG
import com.example.myapplication.R

class ChoiceThemeDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        return builder.setTitle(resources.getString(R.string.textTitleDialogChoiceTheme))
            .setCancelable(true)
            .setPositiveButton(resources.getString(R.string.light_theme)) { dialog, _ ->
                dialog.cancel()
                parentFragmentManager.setFragmentResult(
                    REQUEST_KEY_FOR_DIALOG,
                    bundleOf(KEY_FOR_PRESSED_BUTTON to KEY_FOR_LIGHT),
                )
            }
            .setNegativeButton(resources.getString(R.string.dark_theme)) { dialog, _ ->
                dialog.cancel()
                parentFragmentManager.setFragmentResult(
                    REQUEST_KEY_FOR_DIALOG,
                    bundleOf(KEY_FOR_PRESSED_BUTTON to KEY_FOR_DARK),
                )
            }
            .setNeutralButton(resources.getString(R.string.system_theme)) { dialog, _ ->
                dialog.cancel()
                parentFragmentManager.setFragmentResult(
                    REQUEST_KEY_FOR_DIALOG,
                    bundleOf(KEY_FOR_PRESSED_BUTTON to KEY_FOR_SYSTEM),
                )
            }
            .create()
    }
}
