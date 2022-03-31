package fr.lbc.albums.data.local

import fr.lbc.albums.data.local.db.AlbumDao
import fr.lbc.albums.data.model.Album
import fr.lbc.albums.data.model.entity.AlbumEntity
import javax.inject.Inject

class AlbumLocalDataSourceImpl @Inject constructor(private val albumDao: AlbumDao): AlbumLocalDataSource {

    override fun getAlbums() = albumDao.getAll()

    override fun saveAlbums(albums: List<AlbumEntity>) {
        albumDao.deleteAll()
        albumDao.insertAll(albums)
    }
}