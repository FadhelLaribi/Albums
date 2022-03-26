package fr.lbc.albums.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write
    /**
     * Returns the content and prevents its use again.
     */
    private fun consume(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun consume(onEventUnconsumed: (Event<T>) -> Unit) {
        val data = consume()
        if (data != null) {
            onEventUnconsumed(this)
        }
    }

    fun peek() = content
}

open class MutableEventLiveData<T> : MutableLiveData<List<T>>() {
    private val _value = ArrayList<T>()

    var event: T
        set(value) {
            val e = value as Event<*>
            _value.removeAll { e.hasBeenHandled }
            _value.add(value)
            this.value = _value
        }
        get() = _value.last()
}

typealias EventLiveData<T> = LiveData<List<T>>