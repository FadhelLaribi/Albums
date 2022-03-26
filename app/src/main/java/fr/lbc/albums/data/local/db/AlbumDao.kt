package fr.lbc.albums.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.lbc.albums.data.model.Album
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Insert
    fun insertAll(albums: List<Album>)

    @Query("DELETE FROM album")
    fun deleteAll()

    @Query("SELECT * FROM album")
    fun getAll(): Flow<List<Album>>
}
