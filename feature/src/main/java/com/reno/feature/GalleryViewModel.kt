package com.reno.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.reno.common.model.Band
import com.reno.common.model.PicsumImageResponse
import com.reno.data.GalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    repository: GalleryRepository
) : ViewModel() {
    val band1DataFlow: Flow<Band> = repository.getImageList()
        .distinctUntilChanged()
        .cachedIn(viewModelScope)
        .map { Band("Section 1", it) }

    val band2DataFlow: Flow<Band> = repository.getImageList()
        .distinctUntilChanged()
        .cachedIn(viewModelScope)
        .map { Band("Section 2", it) }

    val band3DataFlow: Flow<Band> = repository.getImageList()
        .distinctUntilChanged()
        .cachedIn(viewModelScope)
        .map { Band("Section 3", it) }

    val pagingFlow: Flow<PagingData<PicsumImageResponse>> = repository.getImageList()
}
