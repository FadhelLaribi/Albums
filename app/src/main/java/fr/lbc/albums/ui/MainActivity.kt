package fr.lbc.albums.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.lbc.albums.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)
    }
}