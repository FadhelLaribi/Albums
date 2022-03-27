package fr.lbc.albums.data.repository

import fr.lbc.albums.data.Result
import fr.lbc.albums.data.model.Album
import fr.lbc.albums.utils.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AlbumFakeRepository: AlbumRepository {

    private var albums: ArrayList<Album> = arrayListOf()
    var shouldReturnError = false

    override fun getAlbums(): Flow<Event<List<Album>>> {
        return flow {
            emit(Event(albums))
        }
    }

    override suspend fun refreshAlbums(): Result<Unit> {
        if (shouldReturnError) return Result.Error(Exception(ERROR))
        return Result.Success(Unit)
    }

    fun setAlbums(albums: ArrayList<Album>) {
        this.albums = albums
    }

    companion object {
        const val ERROR = "Error"
    }
}