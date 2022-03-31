package fr.lbc.albums.data.remote

import fr.lbc.albums.data.model.Album
import fr.lbc.albums.data.Result
import fr.lbc.albums.data.model.to.AlbumTo

interface AlbumRemoteDataSource {
    suspend fun refreshAlbums(): Result<List<AlbumTo>>
}