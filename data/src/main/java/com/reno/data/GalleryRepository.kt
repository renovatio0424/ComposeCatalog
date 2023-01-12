package com.reno.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.reno.common.model.PicsumImageResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GalleryRepository @Inject constructor(
    private val galleryPagingSource: GalleryPagingSource
) {
    fun getImageList(): Flow<PagingData<PicsumImageResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE_PICSUM,
                enablePlaceholders = false,
                prefetchDistance = 10
            ),
            pagingSourceFactory = { galleryPagingSource }
        ).flow
    }

    companion object {
        const val PAGE_SIZE_PICSUM = 30
    }
}
