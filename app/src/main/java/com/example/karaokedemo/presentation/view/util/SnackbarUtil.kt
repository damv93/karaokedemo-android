package com.example.karaokedemo.presentation.view.util

import androidx.core.content.ContextCompat
import com.example.karaokedemo.R
import com.google.android.material.snackbar.Snackbar

fun Snackbar.withInfo(): Snackbar {
    view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
    return this
}

fun Snackbar.withError(): Snackbar {
    view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorError))
    return this
}