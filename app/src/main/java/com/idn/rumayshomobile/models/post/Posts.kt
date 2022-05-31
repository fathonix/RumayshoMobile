package com.idn.rumayshomobile.models.post

import com.redgunner.worddroid.models.post.Content
import com.redgunner.worddroid.models.post.Title

data class Posts(
    val _embedded: Embedded,
    val content: Content,
    val date: String,
    val id: Int,
    val title: Title
)