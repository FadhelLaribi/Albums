package fr.lbc.albums.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Album(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)