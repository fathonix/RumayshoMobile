package com.idn.rumayshomobile.models.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Embedded(
    @field:SerializedName("wp:featuredmedia")
    val wpFeaturedMedia: List<WpFeaturedMedia?>? = null
) : Parcelable