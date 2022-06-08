package com.idn.rumayshomobile.models.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WpFeaturedMedia(
    @field:SerializedName("source_url")
    val sourceUrl: String? = null,
) : Parcelable