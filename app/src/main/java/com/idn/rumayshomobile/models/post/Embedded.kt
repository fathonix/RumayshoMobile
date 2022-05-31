package com.idn.rumayshomobile.models.post

import com.google.gson.annotations.SerializedName
import com.redgunner.worddroid.models.post.WpFeaturedmedia
import com.redgunner.worddroid.models.post.WpTerm

data class Embedded(
    @SerializedName("wp:featuredmedia")
    val wp_FeaturedMedia: List<WpFeaturedmedia>,
    @SerializedName("wp:term")
    val wp_Term: List<List<WpTerm>>
)