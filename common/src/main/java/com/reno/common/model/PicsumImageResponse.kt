package com.reno.common.model

import com.google.gson.annotations.SerializedName

data class PicsumImageResponse(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("download_url")
    val downloadUrl: String
) {
    fun getResizeHeight(resizeWidth: Int): Int = (height / width) * resizeWidth
}
