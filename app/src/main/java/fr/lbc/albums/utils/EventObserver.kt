package fr.lbc.albums.utils

import androidx.lifecycle.Observer

/**
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content has
 * already been handled.
 */

@Suppress("UNCHECKED_CAST")
class EventObserver<R>(private val onEventUnconsumed: (R) -> Unit) : Observer<R> {
    override fun onChanged(value: R) {
        (value as Event<*>).consume(onEventUnconsumed as (Event<*>) -> Unit)
    }
}