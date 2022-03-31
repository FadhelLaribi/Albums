package fr.lbc.albums.data.local

import fr.lbc.albums.data.local.db.AlbumsDao
import fr.lbc.albums.data.model.entity.AlbumEntity
import javax.inject.Inject

class AlbumsLocalDataSourceImpl @Inject constructor(private val albumsDao: AlbumsDao): AlbumsLocalDataSource {

    override fun getAlbums() = albumsDao.getAll()

    override fun saveAlbums(albums: List<AlbumEntity>) {
        albumsDao.deleteAll()
        albumsDao.insertAll(albums)
    }
}