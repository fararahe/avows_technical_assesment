package com.avows.utility.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide


fun ImageView.loadImage(
    url: String,
    errorDrawable: Int? = null,
    placeholderDrawable: Int? = null,
) {
    val loader = Glide
        .with(this)
        .load(url)

    errorDrawable?.let {
        loader.error(it)
    }

    placeholderDrawable?.let {
        loader.placeholder(it)
    }

    loader.into(this)
}