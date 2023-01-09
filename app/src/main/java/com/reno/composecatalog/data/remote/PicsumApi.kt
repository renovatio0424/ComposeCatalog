package com.reno.composecatalog.data.remote

import com.reno.composecatalog.data.model.PicsumImageResponse
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
    ): List<PicsumImageResponse>

    companion object {
        const val BASE_URL = "https://picsum.photos/"
    }
}
