package com.mvvm.retrofit.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mvvm.retrofit.R

@BindingAdapter("android:src")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(url)
            .placeholder(R.drawable.placeholder_image)
            .into(view)
    }
}