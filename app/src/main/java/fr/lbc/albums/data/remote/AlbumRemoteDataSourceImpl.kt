package fr.lbc.albums.data.remote

import fr.lbc.albums.data.remote.service.AlbumService
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject
import fr.lbc.albums.data.Result
import fr.lbc.albums.data.model.to.AlbumTo

class AlbumRemoteDataSourceImpl @Inject constructor(private val albumService: AlbumService) :
    AlbumRemoteDataSource {

    override suspend fun refreshAlbums(): Result<List<AlbumTo>> {
        return try {
            val albums = albumService.getAlbums()
            Result.Success(albums)
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    }
}