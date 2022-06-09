package com.idn.rumayshomobile.models.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TwitterMisc(
    @field:SerializedName("Written by")
    val writtenBy: String? = null,
) : Parcelable