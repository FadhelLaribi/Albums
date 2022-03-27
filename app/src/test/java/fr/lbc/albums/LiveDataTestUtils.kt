package fr.lbc.albums

import fr.lbc.albums.utils.Event
import org.junit.Assert

fun <T> Event<T>.assertEventEqual(expected: Event<T>) {
    val expectedItemType = expected::class.java
    val actualItemType = this::class.java
    Assert.assertEquals(expectedItemType, actualItemType)
    Assert.assertEquals(expected.peek(), peek())
}