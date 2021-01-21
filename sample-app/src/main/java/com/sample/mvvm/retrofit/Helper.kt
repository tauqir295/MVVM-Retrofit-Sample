package com.sample.mvvm.retrofit

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mvvm.retrofit.R

/**
 * method for loading the image from url in the image view
 * @param - [ImageView] - the view on which image will be loaded
 * @param - [url] - path in the server where image is present
 */
@BindingAdapter("android:src")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .placeholder(R.drawable.placeholder_image)
        .into(view)
}