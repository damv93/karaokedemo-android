package com.example.karaokedemo.presentation.view.util

import android.view.View
import androidx.core.content.ContextCompat
import com.example.karaokedemo.R
import com.google.android.material.snackbar.Snackbar

object SnackbarUtil {

    fun showInfo(view: View, message: String, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(view, message, duration)
            .withInfo()
            .show()
    }

    fun showError(view: View, message: String, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(view, message, duration)
            .withError()
            .show()
    }

}

fun Snackbar.withInfo(): Snackbar {
    view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
    return this
}

fun Snackbar.withError(): Snackbar {
    view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorError))
    return this
}