package fr.lbc.albums.data.remote

import fr.lbc.albums.data.remote.service.AlbumsService
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject
import fr.lbc.albums.data.Result
import fr.lbc.albums.data.model.to.AlbumTo

class AlbumsRemoteDataSourceImpl @Inject constructor(private val albumsService: AlbumsService) :
    AlbumsRemoteDataSource {

    override suspend fun refreshAlbums(): Result<List<AlbumTo>> {
        return try {
            val albums = albumsService.getAlbums()
            Result.Success(albums)
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    }
}