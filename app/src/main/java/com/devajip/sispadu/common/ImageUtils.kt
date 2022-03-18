package com.devajip.sispadu.common

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

@Composable
fun loadImage(url: String?, @DrawableRes defaultImage: Int): MutableState<Bitmap?> {

    val bitmapState: MutableState<Bitmap?> = remember {
        mutableStateOf(null)
    }

    Glide.with(LocalContext.current)
        .asBitmap()
        .placeholder(defaultImage)
        .load(defaultImage)
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) { }
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                bitmapState.value = resource
            }
        })

    if (!url.isNullOrEmpty()) {
        val glideUrl = GlideUrl(url, LazyHeaders.Builder()
            .addHeader("User-Agent", "your-user-agent")
            .build())

        Glide.with(LocalContext.current)
            .asBitmap()
            .load(glideUrl)
            .placeholder(defaultImage)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmapState.value = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    return bitmapState
}

@Composable
fun loadImage(@DrawableRes drawable: Int) {
    val bitmapState: MutableState<Bitmap?> = remember {
        mutableStateOf(null)
    }

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(drawable)
        .into(object: CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmapState.value = resource
            }
            override fun onLoadCleared(placeholder: Drawable?) { }
        })
}