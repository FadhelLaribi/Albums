package fr.lbc.albums.data.local

import fr.lbc.albums.data.model.entity.AlbumEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AlbumsFakeLocalDataSource : AlbumsLocalDataSource {

    private val albums: ArrayList<AlbumEntity> = arrayListOf()

    override fun getAlbums(): Flow<List<AlbumEntity>> {
        return flow {
            emit(albums)
        }
    }

    override fun saveAlbums(albums: List<AlbumEntity>) {
        this.albums.clear()
        this.albums.addAll(albums)
    }
}