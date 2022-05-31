package com.idn.rumayshomobile.models.category

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Categories(
    val count: Int,
    val id: Int,
    val name: String
) : Parcelable