package com.idn.rumayshomobile.models.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Content(
    @field:SerializedName("rendered")
    val rendered: String? = null
) : Parcelable