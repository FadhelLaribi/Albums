package fr.lbc.albums.data.remote

import fr.lbc.albums.data.Result
import fr.lbc.albums.data.model.to.AlbumTo
import java.lang.Exception

class AlbumsFakeRemoteDataSource(var albums: MutableList<AlbumTo>? = mutableListOf()) :
    AlbumsRemoteDataSource {

    override suspend fun refreshAlbums(): Result<List<AlbumTo>> {
        if (albums != null) return Result.Success(albums!!)
        return Result.Error(Exception("Error fetching albums !"))
    }
}