package fr.lbc.albums.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import fr.lbc.albums.databinding.ActivityMainBinding
import fr.lbc.albums.utils.EventObserver
import fr.lbc.albums.utils.MultipleEventObserver

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)

        setSupportActionBar(binding.toolbar)

        val adapter = AlbumsAdapter(binding.emptyView)
        binding.recyclerView.adapter = adapter

        val swipeRefresh = binding.swipeRefresh

        val viewModel = ViewModelProvider(this)[AlbumsViewModel::class.java].apply {

            albumsLiveData.observe(this@MainActivity, EventObserver { event ->
                adapter.setAlbums(event.peek())
            })

            uiState.observe(this@MainActivity, MultipleEventObserver { event ->
                when (event) {
                    is SetRefreshing -> swipeRefresh.isRefreshing = event.peek()
                    is ShowError -> Snackbar.make(root, event.peek(), Snackbar.LENGTH_LONG).show()
                }
            })
        }

        swipeRefresh.setOnRefreshListener {
            viewModel.refreshAlbums()
        }

        if (savedInstanceState == null)
            viewModel.refreshAlbums()
    }
}