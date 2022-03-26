package fr.lbc.albums.data.local

import fr.lbc.albums.data.local.db.AlbumDao
import fr.lbc.albums.data.model.Album
import javax.inject.Inject

class AlbumLocalDataSourceImpl @Inject constructor(private val albumDao: AlbumDao): AlbumLocalDataSource {

    override fun getAlbums() = albumDao.getAll()

    override fun saveAlbums(albums: List<Album>) {
        albumDao.deleteAll()
        albumDao.insertAll(albums)
    }
}