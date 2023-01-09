package com.reno.composecatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.reno.composecatalog.paging.PagingBandScreen
import com.reno.composecatalog.ui.theme.ComposeCatalogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCatalogTheme {
                PagingBandScreen()
            }
        }
    }
}
