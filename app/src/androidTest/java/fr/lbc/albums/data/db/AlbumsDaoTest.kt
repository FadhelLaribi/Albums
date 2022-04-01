package fr.lbc.albums.data.db

import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import fr.lbc.albums.TestAppModule.TestDatabase
import fr.lbc.albums.data.local.db.AlbumsDao
import fr.lbc.albums.data.local.db.AppDatabase
import fr.lbc.albums.data.model.entity.AlbumEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@MediumTest
class AlbumsDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @TestDatabase
    @Inject
    lateinit var database: AppDatabase

    private val firstAlbum = AlbumEntity(1, 1, "album1", "url", "thumbnailUrl")
    private val secondAlbum = AlbumEntity(2, 1, "album2", "url", "thumbnailUrl")

    private lateinit var albumsDao: AlbumsDao

    @Before
    fun setUp() {
        hiltRule.inject()
        albumsDao = database.albumDao()
    }

    @Test
    fun insertAll_getAll_albumsShouldBeSavedThenReturned() = runTest {
        // ARRANGE
        val albums = listOf(firstAlbum, secondAlbum)

        // ACT
        albumsDao.insertAll(albums)
        val insertedAlbums = albumsDao.getAll().first()

        // ASSERT
        assertEquals(insertedAlbums, albums)
    }

    @Test
    fun insertAll_deleteAll_getAll_emptyListShouldBeReturned() = runTest {
        // ARRANGE
        val albums = listOf(firstAlbum, secondAlbum)

        // ACT
        albumsDao.insertAll(albums)
        albumsDao.deleteAll()
        val storedAlbums = albumsDao.getAll().first()

        // ASSERT
        assertTrue(storedAlbums.isEmpty())
    }

    @After
    fun tearDown() {
        database.close()
    }
}