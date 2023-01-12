package com.reno.common.model

import androidx.paging.PagingData

data class Band(
    val title: String,
    val pagingData: PagingData<PicsumImageResponse>
)
