package fr.lbc.albums.data

import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import fr.lbc.albums.TestAppModule.TestDatabase
import fr.lbc.albums.data.local.AlbumLocalDataSource
import fr.lbc.albums.data.local.AlbumLocalDataSourceImpl
import fr.lbc.albums.data.local.db.AlbumDao
import fr.lbc.albums.data.local.db.AppDatabase
import fr.lbc.albums.data.model.Album
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
class AlbumLocalDataSourceTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @TestDatabase
    @Inject
    lateinit var database: AppDatabase

    private val firstAlbum = Album(1, 1, "album1", "url", "thumbnailUrl")
    private val secondAlbum = Album(2, 1, "album1", "url", "thumbnailUrl")

    private lateinit var albumDao: AlbumDao
    private lateinit var dataSource: AlbumLocalDataSource

    @Before
    fun setUp() {
        hiltRule.inject()
        albumDao = database.albumDao()
        dataSource = AlbumLocalDataSourceImpl(albumDao)
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
    fun saveAlbums_getAlbums_should_save_then_return_provided_albums_while_deleting_old_ones() = runTest {
        // ARRANGE
        val oldAlbums = arrayListOf(firstAlbum, secondAlbum)
        dataSource.saveAlbums(oldAlbums)
        val thirdAlbum = Album(3, 1, "album3", "url", "thumbnailUrl")
        val fourthAlbum = Album(4, 1, "album4", "url", "thumbnailUrl")
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