package com.reno.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API Docs : https://picsum.photos/
 * */
interface PicsumApi {
    @GET("v2/list")
    suspend fun getImageList(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 30
    ): List<com.reno.common.model.PicsumImageResponse>

    companion object {
        const val BASE_URL = "https://picsum.photos/"
    }
}
