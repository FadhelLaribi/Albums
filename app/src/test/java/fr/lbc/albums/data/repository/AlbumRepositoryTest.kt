package fr.lbc.albums.data.repository

import fr.lbc.albums.data.Result
import fr.lbc.albums.assertEventEqual
import fr.lbc.albums.data.local.AlbumFakeLocalDataSource
import fr.lbc.albums.data.model.Album
import fr.lbc.albums.data.remote.AlbumFakeRemoteDataSource
import fr.lbc.albums.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AlbumRepositoryTest {

    private val firstAlbum = Album(1, 1, "album 1", "url", "url")
    private val secondAlbum = Album(1, 2, "album 2", "url", "url")

    private lateinit var remoteDataSource: AlbumFakeRemoteDataSource
    private lateinit var localDataSource: AlbumFakeLocalDataSource
    private lateinit var albumRepository: AlbumRepositoryImpl

    @Before
    fun createRepository() {
        remoteDataSource = AlbumFakeRemoteDataSource()
        localDataSource = AlbumFakeLocalDataSource()
        albumRepository = AlbumRepositoryImpl(remoteDataSource, localDataSource, Dispatchers.IO)
    }

    @Test
    fun `get albums should return albums list from the local data source`() = runTest {
        // ARRANGE
        val albums: List<Album> = arrayListOf(firstAlbum)
        localDataSource.saveAlbums(albums)
        val expected = Event(albums)

        // ACT
        val actual = albumRepository.getAlbums().first()

        // ASSERT
        expected.assertEventEqual(actual)
    }

    @Test
    fun `get albums should return empty list when local data source is empty`() = runTest {
        // ARRANGE
        localDataSource.saveAlbums(emptyList())

        // ACT
        val actual = albumRepository.getAlbums().first()

        // ASSERT
        assertTrue(actual.peek().isEmpty())
    }

    @Test
    fun `refresh album should return success if remote data source succeeds`() = runTest {
        // ARRANGE

        // ACT
        val actual = albumRepository.refreshAlbums()

        // ASSERT
        assertTrue(actual is Result.Success)
    }

    @Test
    fun `refresh album should return error if remote data source fails`() = runTest {
        // ARRANGE
        remoteDataSource.albums = null

        // ACT
        val actual = albumRepository.refreshAlbums()

        // ASSERT
        assertTrue(actual is Result.Error)
    }

    @Test
    fun `refresh album should store data in local data source if remote data source succeeds`() =
        runTest {
            // ARRANGE
            val albums = arrayListOf(firstAlbum, secondAlbum)
            remoteDataSource.albums = albums
            val expected = Event(albums)

            // ACT
            albumRepository.refreshAlbums()
            val actual = albumRepository.getAlbums().first()

            // ASSERT
            expected.assertEventEqual(actual)
        }
}