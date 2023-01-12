package com.reno.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.reno.common.model.PicsumImageResponse

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
        Text(modifier = Modifier.padding(8.dp), text = title)
        LazyRow(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxHeight(0.2f)
        ) {
            items(
                count = band.itemCount,
                key = { band[it]?.id ?: 0 }
            ) { index ->
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

                BandItemView(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .height(200.dp)
                        .width(200.dp),
                    picsumImageResponse = band[index]
                )
            }
        }
    }
}

@Composable
fun ErrorView(error: Throwable) {
    Text(
        text = error.message ?: "Unknown Error",
        style = MaterialTheme.typography.h6,
        color = MaterialTheme.colors.error
    )
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .height(200.dp)
            .width(200.dp)
            .background(color = Color.Gray)
    )
}

@Composable
fun BandItemView(modifier: Modifier, picsumImageResponse: PicsumImageResponse?) {
    picsumImageResponse ?: return

    SubcomposeAsyncImage(
        modifier = modifier
            .padding(8.dp),
        model = ImageRequest.Builder(LocalContext.current)
            .data(picsumImageResponse.downloadUrl)
            .memoryCacheKey(picsumImageResponse.downloadUrl)
            .diskCacheKey(picsumImageResponse.downloadUrl)
            .diskCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .build(),
        loading = {
            Box(
                modifier = modifier
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .width(200.dp)
                    .height(200.dp)
                    .padding(8.dp)
            )
        },
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}

@Preview(showBackground = true)
@Composable
fun PagingBandPreview() {
    BandItemView(
        modifier = Modifier,
        picsumImageResponse = PicsumImageResponse(
            id = "id",
            author = "reno",
            width = 300,
            height = 300,
            url = "https://picsum.photos/300/300",
            downloadUrl = "https://picsum.photos/200/300"
        )
    )
}
