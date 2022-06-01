package com.idn.rumayshomobile.models.tag

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tags(
	val count: Int,
	val id: Int,
	val name: String
) : Parcelable
