package fr.lbc.albums.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import fr.lbc.albums.data.service.AlbumService
import fr.lbc.albums.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var albumService: AlbumService
    @Inject lateinit var dispatcherIO: CoroutineDispatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)

        setSupportActionBar(binding.toolbar)

        lifecycleScope.launch(dispatcherIO) {
            val albums = albumService.getAlbums()
            albums.forEach {
                Timber.d("Album ${it.id} = ${it.title}")
            }
        }

    }
}