package com.example.karaokedemo.presentation.view.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.karaokedemo.R
import com.squareup.picasso.Picasso

@BindingAdapter("imageUri")
fun setImageUri(imageView: ImageView, uri: String?) {
    Picasso.get()
        .load(uri)
        .placeholder(R.drawable.anim_loading)
        .into(imageView)
}