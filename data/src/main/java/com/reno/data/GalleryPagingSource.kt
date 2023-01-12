package com.reno.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.reno.common.model.PicsumImageResponse
import com.reno.data.remote.PicsumApi
import javax.inject.Inject

class GalleryPagingSource @Inject constructor(
    private val picsumApi: PicsumApi
) : PagingSource<Int, PicsumImageResponse>() {
    override fun getRefreshKey(state: PagingState<Int, PicsumImageResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PicsumImageResponse> {
        return try {
            val nextPageIdx = params.key ?: INDEX_TO_START_PAGE
            val imageList = picsumApi.getImageList(nextPageIdx)
            LoadResult.Page(
                data = imageList,
                prevKey = null,
                nextKey = nextPageIdx + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        private const val INDEX_TO_START_PAGE = 0
    }
}
