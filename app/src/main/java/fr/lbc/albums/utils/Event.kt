package fr.lbc.albums.utils

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