package fr.lbc.albums.data.local

import fr.lbc.albums.data.model.entity.AlbumEntity
import kotlinx.coroutines.flow.Flow

interface AlbumsLocalDataSource {
    fun getAlbums(): Flow<List<AlbumEntity>>
    fun saveAlbums(albums: List<AlbumEntity>)
}