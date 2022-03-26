package fr.lbc.albums.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.lbc.albums.R
import fr.lbc.albums.data.Result
import fr.lbc.albums.data.model.Album
import fr.lbc.albums.data.repository.AlbumRepository
import fr.lbc.albums.utils.Event
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(private val repository: AlbumRepository) : ViewModel() {

    private var _albumsLiveData = MutableLiveData<List<Album>>()
    val albumsLiveData: LiveData<List<Album>> = _albumsLiveData

    private var _showError = MutableLiveData<Event<Int>>()
    val showError: LiveData<Event<Int>> = _showError

    init {
        loadAlbums()
    }

    private fun loadAlbums() {
        viewModelScope.launch {
            val result = repository.getAlbums()
            if (result is Result.Success) {
                _albumsLiveData.value = result.data
            } else {
                Timber.e("Error : ${(result as Result.Error).exception}")
                _showError.value = Event(R.string.generic_error)
            }
        }
    }

}