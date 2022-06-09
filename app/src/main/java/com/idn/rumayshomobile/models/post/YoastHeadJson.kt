package com.idn.rumayshomobile.models.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class YoastHeadJson(
	@field:SerializedName("twitter_misc")
	val twitterMisc: TwitterMisc? = null
) : Parcelable