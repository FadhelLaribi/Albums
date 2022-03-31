package fr.lbc.albums.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.lbc.albums.data.model.entity.AlbumEntity

@Database(entities = [AlbumEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumsDao
}