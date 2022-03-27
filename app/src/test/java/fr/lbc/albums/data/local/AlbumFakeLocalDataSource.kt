package fr.lbc.albums.data.local

import fr.lbc.albums.data.model.Album
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AlbumFakeLocalDataSource : AlbumLocalDataSource {

    private val albums: ArrayList<Album> = arrayListOf()

    override fun getAlbums(): Flow<List<Album>> {
        return flow {
            emit(albums)
        }
    }

    override fun saveAlbums(albums: List<Album>) {
        this.albums.clear()
        this.albums.addAll(albums)
    }
}