package fr.lbc.albums.data.local

import fr.lbc.albums.data.model.Album
import kotlinx.coroutines.flow.Flow

interface AlbumLocalDataSource {
    fun getAlbums(): Flow<List<Album>>
    fun saveAlbums(albums: List<Album>)
}