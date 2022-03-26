package fr.lbc.albums.data.repository

import fr.lbc.albums.data.remote.AlbumRemoteDataSource
import fr.lbc.albums.data.Result
import fr.lbc.albums.data.local.AlbumLocalDataSource
import fr.lbc.albums.utils.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ExperimentalCoroutinesApi
class AlbumRepositoryImpl @Inject constructor(
    private val remoteDataSource: AlbumRemoteDataSource,
    private val localDataSource: AlbumLocalDataSource,
    private val dispatcher: CoroutineDispatcher
) : AlbumRepository {

    override fun getAlbums() = localDataSource.getAlbums().mapLatest { Event(it) }.flowOn(dispatcher)

    override suspend fun refreshAlbums(): Result<Unit> {
        return withContext(dispatcher) {
            val result = remoteDataSource.refreshAlbums()
            if (result is Result.Success) {
                localDataSource.saveAlbums(result.data)
                Result.Success(Unit)
            } else result as Result.Error
        }
    }
}