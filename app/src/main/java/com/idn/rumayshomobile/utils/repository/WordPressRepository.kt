package com.idn.rumayshomobile.utils.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.idn.rumayshomobile.utils.network.WordpressApi
import com.idn.rumayshomobile.utils.paging.CategoryPagingSource
import com.idn.rumayshomobile.utils.paging.PostPagingSource
import com.idn.rumayshomobile.utils.paging.TagPagingSource

class WordPressRepository constructor(private val wordpressApi: WordpressApi) {

    fun getPosts() = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            PostPagingSource(wordpressApi)
        }
    ).liveData

    fun getPostByCategory(categoryId: Int) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            PostPagingSource(
                categoryId = categoryId,
                wordpressApi = wordpressApi
            )
        }
    ).liveData

    fun getPostByTag(tagId: Int) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            PostPagingSource(
                tagId = tagId,
                wordpressApi = wordpressApi
            )
        }
    ).liveData

    fun getCategories() = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            CategoryPagingSource(wordpressApi)
        }
    ).liveData

    fun getTags() = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            TagPagingSource(wordpressApi)
        }
    ).liveData

}