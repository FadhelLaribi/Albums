package fr.lbc.albums.utils

import android.webkit.WebSettings.getDefaultUserAgent
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders.Builder
import fr.lbc.albums.R

fun ImageView.loadUrl(url: String) {
    val context = context
    val headers = Builder().addHeader("User-Agent", getDefaultUserAgent(context)).build()
    Glide.with(context).load(GlideUrl(url, headers)).placeholder(R.color.color_grey).into(this)
}