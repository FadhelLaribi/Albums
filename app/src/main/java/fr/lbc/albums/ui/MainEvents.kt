package fr.lbc.albums.ui

import fr.lbc.albums.utils.Event

sealed class MainEvent<out T>(content: T) : Event<T>(content)

class SetRefreshing(refreshing: Boolean) : MainEvent<Boolean>(refreshing)
class ShowError(resource: Int) : MainEvent<Int>(resource)