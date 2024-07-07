

package com.github.shadowsocks.utils

import androidx.recyclerview.widget.SortedList

private sealed class ArrayIterator<out T> : Iterator<T> {
    abstract val size: Int
    abstract operator fun get(index: Int): T
    private var count = 0
    override fun hasNext() = count < size
    override fun next(): T = if (hasNext()) this[count++] else throw NoSuchElementException()
}

private class SortedListIterator<out T>(private val list: SortedList<T>) : ArrayIterator<T>() {
    override val size get() = list.size()
    override fun get(index: Int) = list[index]
}
fun <T> SortedList<T>.asIterable() = Iterable { SortedListIterator(this) }
