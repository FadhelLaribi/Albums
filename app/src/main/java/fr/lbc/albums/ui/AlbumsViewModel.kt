package fr.lbc.albums.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.lbc.albums.R
import fr.lbc.albums.data.Result
import fr.lbc.albums.data.model.Album
import fr.lbc.albums.data.repository.AlbumsRepository
import fr.lbc.albums.utils.EventLiveData
import fr.lbc.albums.utils.MutableEventLiveData
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(private val repository: AlbumsRepository) : ViewModel() {

    val albumsLiveData: LiveData<List<Album>> = repository.getAlbums().asLiveData()

    private var _uiState = MutableEventLiveData<MainEvent<Any>>()
    val uiState: EventLiveData<MainEvent<Any>> = _uiState

    fun refreshAlbums() {
        viewModelScope.launch {
            _uiState.event = SetRefreshing(true)
            val result = repository.refreshAlbums()
            if (result is Result.Success) {
                Timber.d("Albums fetched successfully !!")
            } else {
                Timber.e("Error : ${(result as Result.Error).exception}")
                _uiState.event = ShowError(R.string.generic_error)
            }
            _uiState.event = SetRefreshing(false)
        }
    }
}