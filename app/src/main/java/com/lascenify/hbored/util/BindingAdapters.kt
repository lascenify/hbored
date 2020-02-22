package com.lascenify.hbored.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imagePath: String) {
    Picasso.with(view.context)
        .load(imagePath)
        .into(view)
}