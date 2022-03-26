package fr.lbc.albums.data.repository

import fr.lbc.albums.data.model.Album
import fr.lbc.albums.data.remote.AlbumRemoteDataSource
import fr.lbc.albums.data.Result
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(private val remoteDataSource: AlbumRemoteDataSource) :
    AlbumRepository {

    override suspend fun getAlbums(): Result<List<Album>> {
        return remoteDataSource.getAlbums()
    }
}