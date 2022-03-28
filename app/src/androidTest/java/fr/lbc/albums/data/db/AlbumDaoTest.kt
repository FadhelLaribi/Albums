package fr.lbc.albums.data.db

import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import fr.lbc.albums.TestAppModule.TestDatabase
import fr.lbc.albums.data.local.db.AlbumDao
import fr.lbc.albums.data.local.db.AppDatabase
import fr.lbc.albums.data.model.Album
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
class AlbumDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @TestDatabase
    @Inject
    lateinit var database: AppDatabase

    private val firstAlbum = Album(1, 1, "album1", "url", "thumbnailUrl")
    private val secondAlbum = Album(2, 1, "album2", "url", "thumbnailUrl")

    private lateinit var albumDao: AlbumDao

    @Before
    fun setUp() {
        hiltRule.inject()
        albumDao = database.albumDao()
    }

    @Test
    fun insertAll_getAll_albumsShouldBeSavedThenReturned() = runTest {
        // ARRANGE
        val albums = listOf(firstAlbum, secondAlbum)

        // ACT
        albumDao.insertAll(albums)
        val insertedAlbums = albumDao.getAll().first()

        // ASSERT
        assertEquals(insertedAlbums, albums)
    }

    @Test
    fun insertAll_deleteAll_getAll_emptyListShouldBeReturned() = runTest {
        // ARRANGE
        val albums = listOf(firstAlbum, secondAlbum)

        // ACT
        albumDao.insertAll(albums)
        albumDao.deleteAll()
        val storedAlbums = albumDao.getAll().first()

        // ASSERT
        assertTrue(storedAlbums.isEmpty())
    }

    @After
    fun tearDown() {
        database.close()
    }
}