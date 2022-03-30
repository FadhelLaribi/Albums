package fr.lbc.albums.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import fr.lbc.albums.utils.applySystemTheme
import timber.log.Timber

@HiltAndroidApp
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        applySystemTheme()
    }
}