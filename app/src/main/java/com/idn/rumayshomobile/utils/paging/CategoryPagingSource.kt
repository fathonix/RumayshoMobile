package com.idn.rumayshomobile.utils.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.idn.rumayshomobile.models.collection.Collection
import com.idn.rumayshomobile.utils.network.WordpressApi
import retrofit2.HttpException
import java.io.IOException

class CategoryPagingSource(private val wordpressApi: WordpressApi) : PagingSource<Int, Collection>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Collection> {
        return try {
            val position = params.key ?: 1

            val categories =
                wordpressApi.getCategories(
                    page = position,
                    perPage = params.loadSize,
                    fields = "id,count,name",
                )

            LoadResult.Page(
                data = categories,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (categories.isEmpty()) null else position + 1
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Collection>): Int? {
        return state.anchorPosition
    }

}