package fr.lbc.albums.data.repository

import fr.lbc.albums.data.Result
import fr.lbc.albums.data.model.Album
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {
    fun getAlbums(): Flow<List<Album>>
    suspend fun refreshAlbums(): Result<Unit>
}