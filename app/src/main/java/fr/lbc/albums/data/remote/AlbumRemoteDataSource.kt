package fr.lbc.albums.data.remote

import fr.lbc.albums.data.model.Album
import fr.lbc.albums.data.Result

interface AlbumRemoteDataSource {
    suspend fun refreshAlbums(): Result<List<Album>>
}