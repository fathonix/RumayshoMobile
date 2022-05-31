package com.idn.rumayshomobile.models.tag

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tags(

	@field:SerializedName("Tag")
	val tag: List<TagItem?>? = null
) : Parcelable
