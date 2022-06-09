package com.idn.rumayshomobile.models.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Posts(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("date_gmt")
    val dateGmt: String? = null,

    @field:SerializedName("title")
    val title: Title? = null,

    @field:SerializedName("content")
    val content: Content? = null,

    @field:SerializedName("_embedded")
    val embedded: Embedded? = null,

    @field:SerializedName("yoast_head_json")
    val yoastHeadJson: YoastHeadJson? = null
) : Parcelable