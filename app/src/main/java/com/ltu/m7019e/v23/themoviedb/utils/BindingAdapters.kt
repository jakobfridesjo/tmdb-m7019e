package com.ltu.m7019e.v23.themoviedb.utils

import android.annotation.SuppressLint
import android.graphics.Color
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ltu.m7019e.v23.themoviedb.R
import com.ltu.m7019e.v23.themoviedb.utils.Constants.YOUTUBE_BASE_URL
import java.text.SimpleDateFormat
import java.util.Locale


@BindingAdapter("posterImageUrl")
fun bindPosterImage(imgView: ImageView, imgUrl:String?) {
    imgUrl?.let { posterPath ->
        Glide
            .with(imgView)
            .load(Constants.POSTER_IMAGE_BASE_URL + Constants.POSTER_IMAGE_WIDTH + posterPath)
            .into(imgView)
    }
}

@BindingAdapter("avatarImageUrl")
fun bindAvatarImage(imgView: ImageView, imgUrl: String?) {
    val imageUrl = when {
        imgUrl.isNullOrEmpty() -> R.drawable.ic_baseline_person_96
        imgUrl.startsWith("/https://secure.gravatar.com/") -> "https:${imgUrl.substringAfter(":")}"
        else -> Constants.AVATAR_IMAGE_BASE_URL + Constants.AVATAR_IMAGE_WIDTH + imgUrl
    }

    Glide.with(imgView)
        .load(imageUrl)
        .into(imgView)
}


@BindingAdapter("backdropImageUrl")
fun bindBackdropImage(imgView: ImageView, imgUrl:String?) {
    imgUrl?.let { backdropPath ->
        Glide
            .with(imgView)
            .load(Constants.BACKDROP_IMAGE_BASE_URL + Constants.BACKDROP_IMAGE_WIDTH + backdropPath)
            .into(imgView)
    }
}

@BindingAdapter("createdAtDate")
fun bindCreatedAtDate(textView: TextView, createdAt: String?) {
    if (createdAt != null) {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(createdAt)
        val formattedDate = date?.let { outputFormat.format(it) }
        textView.text = formattedDate
    } else {
        textView.text = ""
    }
}

@SuppressLint("SetJavaScriptEnabled")
@BindingAdapter("videoUrl")
fun bindVideoView(webView: WebView, key: String?) {

    val videoUrl = "${YOUTUBE_BASE_URL}${key}"
    val html = "<iframe width=\"100%\" height=\"100%\" src=\"${videoUrl}\" frameBorder=\"0\" allowFullscreen></iframe>"

    webView.settings.javaScriptEnabled = true
    webView.setBackgroundColor(Color.TRANSPARENT)

    webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
}
