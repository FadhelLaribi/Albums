package fr.lbc.albums.data.repository

import fr.lbc.albums.data.model.Album
import fr.lbc.albums.data.Result
import fr.lbc.albums.utils.Event
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {
    fun getAlbums(): Flow<Event<List<Album>>>
    suspend fun refreshAlbums(): Result<Unit>
}