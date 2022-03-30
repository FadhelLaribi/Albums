package fr.lbc.albums.utils

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate

fun applySystemTheme() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
    }
}

fun isDarkThemeEnabled(context: Context) =
    context.resources.configuration.uiMode and UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES
