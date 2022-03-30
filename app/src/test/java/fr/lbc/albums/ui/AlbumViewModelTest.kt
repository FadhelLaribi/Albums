package fr.lbc.albums.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import fr.lbc.albums.R
import fr.lbc.albums.assertEventsEqual
import fr.lbc.albums.data.model.Album
import fr.lbc.albums.data.repository.AlbumFakeRepository
import fr.lbc.albums.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AlbumsViewModelTest {

    private lateinit var repository: AlbumFakeRepository
    private lateinit var viewModel: AlbumsViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = AlbumFakeRepository()
        viewModel = AlbumsViewModel(repository)
    }

    @Test
    fun `albums live data should emit same albums as repository get albums`() = runTest {
        // ARRANGE
        val firstAlbum = Album(1, 1, "album 1", "url", "url")
        val secondAlbum = Album(1, 2, "album 2", "url", "url")
        val expected = arrayListOf(firstAlbum, secondAlbum)
        repository.setAlbums(expected)

        // ACT
        val actual = viewModel.albumsLiveData.getOrAwaitValue()

        // ASSERT
        assertEquals(expected, actual)
    }

    @Test
    fun `albums live data should emit empty value when repository has no albums`() = runTest {
        // ARRANGE
        repository.shouldReturnError = true

        // ACT
        val albumsLiveData = viewModel.albumsLiveData

        // ASSERT
        Assert.assertTrue(albumsLiveData.getOrAwaitValue().isEmpty())
    }

    @Test
    fun `ui state live data should emit set refreshing true show error then set refreshing false when refresh album call fails`() =
        runTest {
            // ARRANGE
            repository.shouldReturnError = true

            // ACT
            viewModel.refreshAlbums()
            val uiStateLiveData = viewModel.uiState.getOrAwaitValue()
            val expectedUiStateEvent = listOf(
                SetRefreshing(true),
                ShowError(R.string.generic_error),
                SetRefreshing(false)
            )

            // ASSERT
            uiStateLiveData.assertEventsEqual(expectedUiStateEvent)
        }

    @Test
    fun `ui state live data should emit set refreshing true then set refreshing false and no error event when refresh album call succeeds`() =
        runTest {
            // ARRANGE

            // ACT
            viewModel.refreshAlbums()
            val uiStateLiveData = viewModel.uiState.getOrAwaitValue()
            val expectedUiStateEvent = listOf(
                SetRefreshing(true), SetRefreshing(false)
            )

            // ASSERT
            uiStateLiveData.assertEventsEqual(expectedUiStateEvent)
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancelChildren()
    }
}