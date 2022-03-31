package fr.lbc.albums.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.lbc.albums.data.model.Album
import fr.lbc.albums.data.model.entity.AlbumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Insert
    fun insertAll(albums: List<AlbumEntity>)

    @Query("DELETE FROM albums")
    fun deleteAll()

    @Query("SELECT * FROM albums")
    fun getAll(): Flow<List<AlbumEntity>>
}
