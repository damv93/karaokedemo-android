package com.example.karaokedemo.presentation.view.util

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat

object HtmlUtil {

    fun fromHtml(html: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }

}