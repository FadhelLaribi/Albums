package fr.lbc.albums.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import fr.lbc.albums.R
import fr.lbc.albums.databinding.ActivityMainBinding
import fr.lbc.albums.utils.EventObserver
import fr.lbc.albums.utils.MultipleEventObserver
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)

        setSupportActionBar(binding.toolbar)

        val adapter = AlbumsAdapter(binding.emptyView)
        binding.recyclerView.adapter = adapter

        val swipeRefresh = binding.swipeRefresh.apply {
            setColorSchemeResources(R.color.color_primary, R.color.color_secondary)
        }

        val viewModel: AlbumsViewModel by viewModels()
        viewModel.apply {

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