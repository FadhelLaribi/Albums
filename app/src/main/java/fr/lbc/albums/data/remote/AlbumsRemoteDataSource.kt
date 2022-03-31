package fr.lbc.albums.data.remote

import fr.lbc.albums.data.Result
import fr.lbc.albums.data.model.to.AlbumTo

interface AlbumsRemoteDataSource {
    suspend fun refreshAlbums(): Result<List<AlbumTo>>
}