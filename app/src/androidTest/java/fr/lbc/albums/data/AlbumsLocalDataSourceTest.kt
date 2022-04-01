package fr.lbc.albums.data

import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import fr.lbc.albums.TestAppModule.TestDatabase
import fr.lbc.albums.data.local.AlbumsLocalDataSource
import fr.lbc.albums.data.local.AlbumsLocalDataSourceImpl
import fr.lbc.albums.data.local.db.AlbumsDao
import fr.lbc.albums.data.local.db.AppDatabase
import fr.lbc.albums.data.model.entity.AlbumEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@MediumTest
class AlbumsLocalDataSourceTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @TestDatabase
    @Inject
    lateinit var database: AppDatabase

    private val firstAlbum = AlbumEntity(1, 1, "album1", "url", "thumbnailUrl")
    private val secondAlbum = AlbumEntity(2, 1, "album1", "url", "thumbnailUrl")

    private lateinit var albumsDao: AlbumsDao
    private lateinit var dataSource: AlbumsLocalDataSource

    @Before
    fun setUp() {
        hiltRule.inject()
        albumsDao = database.albumDao()
        dataSource = AlbumsLocalDataSourceImpl(albumsDao)
    }

    @Test
    fun saveAlbums_should_insert_albums() = runTest {
        // ARRANGE
        val expected = arrayListOf(firstAlbum, secondAlbum)

        // ACT
        dataSource.saveAlbums(expected)
        val actual = dataSource.getAlbums().first()

        // ASSERT
        assertEquals(expected, actual)
    }

    @Test
    fun saveAlbums_getAlbums_should_save_then_return_provided_albums_while_deleting_old_ones() =
        runTest {
            // ARRANGE
            val oldAlbums = arrayListOf(firstAlbum, secondAlbum)
            dataSource.saveAlbums(oldAlbums)
            val thirdAlbum = AlbumEntity(3, 1, "album3", "url", "thumbnailUrl")
            val fourthAlbum = AlbumEntity(4, 1, "album4", "url", "thumbnailUrl")
            val expected = arrayListOf(thirdAlbum, fourthAlbum)

            // ACT
            dataSource.saveAlbums(expected)
            val actual = dataSource.getAlbums().first()

            // ASSERT
            assertEquals(expected, actual)
        }

    @After
    fun tearDown() {
        database.close()
    }
}