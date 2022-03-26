package fr.lbc.albums.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.lbc.albums.data.Result
import fr.lbc.albums.data.repository.AlbumRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(private val repository: AlbumRepository) : ViewModel() {

    init {
        loadAlbums()
    }

    private fun loadAlbums() {
        viewModelScope.launch {
            val result = repository.getAlbums()
            if (result is Result.Success) {
                result.data.forEach {
                    Timber.d("|| Album ${it.id} = ${it.title}")
                }
            } else {
                Timber.d("Error retrieving albums : ${(result as Result.Error).exception}")
            }
        }
    }

}