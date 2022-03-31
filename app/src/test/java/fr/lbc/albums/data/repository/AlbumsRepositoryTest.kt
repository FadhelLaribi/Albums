package fr.lbc.albums.data.repository

import fr.lbc.albums.data.Result
import fr.lbc.albums.data.local.AlbumsFakeLocalDataSource
import fr.lbc.albums.data.model.entity.AlbumEntity
import fr.lbc.albums.data.model.mapper.toAlbum
import fr.lbc.albums.data.model.to.AlbumTo
import fr.lbc.albums.data.remote.AlbumsFakeRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AlbumsRepositoryTest {

    private lateinit var remoteDataSource: AlbumsFakeRemoteDataSource
    private lateinit var localDataSource: AlbumsFakeLocalDataSource
    private lateinit var albumRepository: AlbumsRepositoryImpl

    @Before
    fun createRepository() {
        remoteDataSource = AlbumsFakeRemoteDataSource()
        localDataSource = AlbumsFakeLocalDataSource()
        albumRepository = AlbumsRepositoryImpl(remoteDataSource, localDataSource, Dispatchers.IO)
    }

    @Test
    fun `get albums should return albums list from the local data source`() = runTest {
        // ARRANGE
        val storedAlbums = listOf(
            AlbumEntity(1, 1, "album 1", "url", "url"),
            AlbumEntity(2, 1, "album 2", "url", "url")
        )
        localDataSource.saveAlbums(storedAlbums)
        val expected = storedAlbums.map { it.toAlbum() }

        // ACT
        val actual = albumRepository.getAlbums().first()

        // ASSERT
        assertEquals(expected, actual)
    }

    @Test
    fun `get albums should return empty list when local data source is empty`() = runTest {
        // ARRANGE
        localDataSource.saveAlbums(emptyList())

        // ACT
        val actual = albumRepository.getAlbums().first()

        // ASSERT
        assertTrue(actual.isEmpty())
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
            val resultAlbums = arrayListOf(
                AlbumTo(1, 1, "album 1", "url", "url"),
                AlbumTo(2, 1, "album 2", "url", "url")
            )
            remoteDataSource.albums = resultAlbums
            val expected = resultAlbums.map { it.toAlbum() }

            // ACT
            albumRepository.refreshAlbums()
            val actual = albumRepository.getAlbums().first()

            // ASSERT
            assertEquals(expected, actual)

        }
}