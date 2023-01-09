package com.reno.composecatalog.data.di

import com.reno.composecatalog.data.GalleryPagingSource
import com.reno.composecatalog.data.remote.PicsumApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {
    @Module
    @InstallIn(SingletonComponent::class)
    object AppModule {

        @Provides
        fun providePicsumApi(): PicsumApi = Retrofit.Builder()
            .baseUrl(PicsumApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PicsumApi::class.java)

        @Provides
        fun provideGalleryPagingSource(picsumApi: PicsumApi): GalleryPagingSource =
            GalleryPagingSource(picsumApi)
    }
}
