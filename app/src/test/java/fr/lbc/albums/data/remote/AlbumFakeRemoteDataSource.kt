package fr.lbc.albums.data.remote

import fr.lbc.albums.data.Result
import fr.lbc.albums.data.model.Album
import java.lang.Exception

class AlbumFakeRemoteDataSource(var albums: MutableList<Album>? = mutableListOf()) :
    AlbumRemoteDataSource {

    override suspend fun refreshAlbums(): Result<List<Album>> {
        if (albums != null) return Result.Success(albums!!)
        return Result.Error(Exception("Error fetching albums !"))
    }
}