package fr.lbc.albums.utils

import androidx.lifecycle.Observer

/**
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content has
 * already been handled.
 */

@Suppress("UNCHECKED_CAST", "unused")
class EventObserver<R>(private val onEventUnconsumed: (R) -> Unit) : Observer<R> {
    override fun onChanged(value: R) {
        (value as Event<*>).consume(onEventUnconsumed as (Event<*>) -> Unit)
    }
}

@Suppress("UNCHECKED_CAST")
class MultipleEventObserver<R>(private val onEventUnconsumed: (R) -> Unit) : Observer<List<R>> {
    override fun onChanged(value: List<R>) {
        val events = value as List<Event<*>>
        for (event in events) {
            if (!event.hasBeenHandled) {
                event.consume(onEventUnconsumed as (Event<*>) -> Unit)
            }
        }
    }
}