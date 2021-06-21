package com.sh.s1.made.mymovies.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sh.s1.made.mymovies.core.R
import com.sh.s1.made.mymovies.core.utils.Consts.POSTER_BASE_URL

object GlideUtils {
    fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load("$POSTER_BASE_URL$url")
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.image_not_found)
            )
            .into(this)
    }
}