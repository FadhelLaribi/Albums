package fr.lbc.albums.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import fr.lbc.albums.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)

        setSupportActionBar(binding.toolbar)

        ViewModelProvider(this)[AlbumsViewModel::class.java]

    }
}