package com.idn.rumayshomobile.utils.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.idn.rumayshomobile.models.post.Posts
import com.idn.rumayshomobile.utils.network.WordpressApi
import retrofit2.HttpException
import java.io.IOException

class PostPagingSource : PagingSource<Int, Posts> {

    private var categoryId: Int? = null
    private var tagId: Int? = null
    private var keyword: String? = null
    private val wordpressApi: WordpressApi

    constructor(wordpressApi: WordpressApi) : super() {
        this.wordpressApi = wordpressApi
    }

    constructor(categoryId: Int, wordpressApi: WordpressApi) : super() {
        this.categoryId = categoryId
        this.wordpressApi = wordpressApi
    }

    constructor(wordpressApi: WordpressApi, tagId: Int) : super() {
        this.tagId = tagId
        this.wordpressApi = wordpressApi
    }

    constructor(wordpressApi: WordpressApi, keyword: String) : super() {
        this.keyword = keyword
        this.wordpressApi = wordpressApi
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Posts> {
        return try {
            val position = params.key ?: 1

            val posts =
                if (categoryId != null)
                    wordpressApi.getPosts(
                        category = categoryId!!,
                        page = position,
                        perPage = params.loadSize,
                        embed = true,
                    )
                else if (tagId != null)
                    wordpressApi.getPosts(
                        tag = tagId!!,
                        page = position,
                        perPage = params.loadSize,
                        embed = true,
                    )
                else if (keyword != null)
                    wordpressApi.searchPost(
                        keyword = keyword!!,
                        page = position,
                        perPage = params.loadSize,
                        embed = true,
                    )
                else
                    wordpressApi.getPosts(
                        page = position,
                        perPage = params.loadSize,
                        embed = true,
                    )

            LoadResult.Page(
                data = posts,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (posts.isEmpty()) null else position + 1
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Posts>): Int? {
        return state.anchorPosition
    }

}