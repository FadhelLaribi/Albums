package fr.lbc.albums.data.repository

import fr.lbc.albums.data.model.Album
import fr.lbc.albums.data.Result

interface AlbumRepository {
    suspend fun getAlbums(): Result<List<Album>>
}