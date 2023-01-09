package com.reno.composecatalog.paging

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.reno.composecatalog.data.model.PicsumImageResponse
import timber.log.Timber

@Composable
fun PagingBandScreen(
    viewModel: GalleryViewModel = hiltViewModel()
) {
    val bandPagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()

    LazyColumn {
        items(10) {
            BandList(title = "Band $it", band = bandPagingItems)
        }
    }
}

@Composable
fun BandList(title: String, band: LazyPagingItems<PicsumImageResponse>) {
    Column {
        Text(text = title)
        LazyRow(
            modifier = Modifier.padding(8.dp)
                .fillMaxHeight(0.2f)
        ) {
            items(
                count = band.itemCount,
                key = {
                    band[it]?.id ?: 0
                }
            ) { index ->
                Timber.d("test: $band")
                when (val state = band.loadState.prepend) {
                    is LoadState.NotLoading -> Unit
                    is LoadState.Loading -> LoadingView()
                    is LoadState.Error -> ErrorView(state.error)
                }

                when (val state = band.loadState.refresh) {
                    is LoadState.NotLoading -> Unit
                    is LoadState.Loading -> LoadingView()
                    is LoadState.Error -> ErrorView(state.error)
                }

                BandItemView(band[index])
            }
        }
    }
}

@Composable
fun ErrorView(error: Throwable) {
    Timber.e(error)
    Text(
        text = error.message ?: "Unknown Error",
        style = MaterialTheme.typography.h6,
        color = MaterialTheme.colors.error
    )
}

@Composable
fun LoadingView() {
    Box {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun BandItemView(picsumImageResponse: PicsumImageResponse?) {
    picsumImageResponse ?: return

    SubcomposeAsyncImage(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .height(300.dp)
            .padding(8.dp),
        model = ImageRequest.Builder(LocalContext.current)
            .data(picsumImageResponse.downloadUrl)
            .memoryCacheKey(picsumImageResponse.downloadUrl)
            .diskCacheKey(picsumImageResponse.downloadUrl)
            .diskCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .build(),
        loading = {
            LoadingView()
        },
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}

@Preview(showBackground = true)
@Composable
fun PagingBandPreview() {
    BandItemView(
        picsumImageResponse = PicsumImageResponse(
            id = "id",
            author = "reno",
            width = 200,
            height = 300,
            url = "https://picsum.photos/200/300",
            downloadUrl = "https://picsum.photos/200/300"
        )
    )
}
