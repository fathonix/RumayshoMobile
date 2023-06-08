package com.idn.rumayshomobile.utils.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.idn.rumayshomobile.utils.network.WordpressApi
import com.idn.rumayshomobile.utils.paging.CategoryPagingSource
import com.idn.rumayshomobile.utils.paging.PostPagingSource
import com.idn.rumayshomobile.utils.paging.TagPagingSource

class WordPressRepository(private val wordpressApi: WordpressApi) {

    private val pagingConfig = PagingConfig(
        pageSize = 10,
        maxSize = 100,
        enablePlaceholders = false
    )

    fun getPosts() = Pager(
        config = pagingConfig,
        pagingSourceFactory = {
            PostPagingSource(wordpressApi)
        }
    ).liveData

    fun getPostByCategory(categoryId: Int) = Pager(
        config = pagingConfig,
        pagingSourceFactory = {
            PostPagingSource(
                categoryId = categoryId,
                wordpressApi = wordpressApi
            )
        }
    ).liveData

    fun getPostByTag(tagId: Int) = Pager(
        config = pagingConfig,
        pagingSourceFactory = {
            PostPagingSource(
                tagId = tagId,
                wordpressApi = wordpressApi
            )
        }
    ).liveData

    fun getCategories() = Pager(
        config = pagingConfig,
        pagingSourceFactory = {
            CategoryPagingSource(wordpressApi)
        }
    ).liveData

    fun getTags() = Pager(
        config = pagingConfig,
        pagingSourceFactory = {
            TagPagingSource(wordpressApi)
        }
    ).liveData

    fun searchPost(keyword: String) = Pager(
        config = pagingConfig,
        pagingSourceFactory = {
            PostPagingSource(
                keyword = keyword,
                wordpressApi = wordpressApi
            )
        }
    ).liveData

}