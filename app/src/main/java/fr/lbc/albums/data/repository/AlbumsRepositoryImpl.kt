package fr.lbc.albums.data.repository

import fr.lbc.albums.data.Result
import fr.lbc.albums.data.local.AlbumsLocalDataSource
import fr.lbc.albums.data.model.Album
import fr.lbc.albums.data.model.mapper.toAlbum
import fr.lbc.albums.data.model.mapper.toAlbumEntity
import fr.lbc.albums.data.remote.AlbumsRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ExperimentalCoroutinesApi
class AlbumsRepositoryImpl @Inject constructor(
    private val remoteDataSource: AlbumsRemoteDataSource,
    private val localDataSource: AlbumsLocalDataSource,
    private val dispatcher: CoroutineDispatcher
) : AlbumsRepository {

    override fun getAlbums(): Flow<List<Album>> {
        return localDataSource.getAlbums().mapLatest { list -> list.map { it.toAlbum() } }
            .flowOn(dispatcher)
    }

    override suspend fun refreshAlbums(): Result<Unit> {
        return withContext(dispatcher) {
            val result = remoteDataSource.refreshAlbums()
            if (result is Result.Success) {
                // Deleting all the stored albums and inserting the new ones is not very optimised.
                // For API calls in real scenarios we can use etag to avoid unnecessary treatment
                // if data is unchanged. For huge contents of data like this case it's better
                // to use pagination for the web services that support it.
                // Pagination allows us to load and display small portions of data at a time
                // and synchronize it with local storage.Loading partial data on demand reduces
                // usage of network bandwidth and system resources.
                localDataSource.saveAlbums(result.data.map { it.toAlbumEntity() })
                Result.Success(Unit)
            } else result as Result.Error
        }
    }
}