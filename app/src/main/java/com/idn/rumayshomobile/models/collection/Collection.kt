package com.idn.rumayshomobile.models.collection

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Collection(
    val count: Int,
    val id: Int,
    val name: String
) : Parcelable