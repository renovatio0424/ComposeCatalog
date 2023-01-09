package com.reno.composecatalog.data.model

import androidx.paging.PagingData

data class Band(
    val title: String,
    val pagingData: PagingData<PicsumImageResponse>
)
