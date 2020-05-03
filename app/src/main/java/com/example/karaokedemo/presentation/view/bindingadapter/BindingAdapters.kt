package com.example.karaokedemo.presentation.view.bindingadapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.karaokedemo.R
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

@BindingAdapter("imageUri")
fun setImageUri(imageView: ImageView, uri: String?) {
    Picasso.get()
        .load(uri)
        .placeholder(R.drawable.anim_loading)
        .error(R.drawable.ic_broken_image)
        .into(imageView)
}

@BindingAdapter("imageUri", "placeholder")
fun setImageUri(imageView: ImageView, uri: String?, placeholder: Drawable) {
    Picasso.get()
        .load(uri)
        .placeholder(placeholder)
        .transform(CropCircleTransformation())
        .error(R.drawable.ic_broken_image)
        .into(imageView)
}