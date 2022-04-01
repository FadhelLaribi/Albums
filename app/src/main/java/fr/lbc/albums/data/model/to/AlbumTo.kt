package fr.lbc.albums.data.model.to

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlbumTo(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)