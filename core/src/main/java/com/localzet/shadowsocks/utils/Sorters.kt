

package com.localzet.shadowsocks.utils

import androidx.recyclerview.widget.SortedList
import java.net.URL

abstract class BaseSorter<T> : SortedList.Callback<T>() {
    override fun onInserted(position: Int, count: Int) { }
    override fun areContentsTheSame(oldItem: T?, newItem: T?): Boolean = oldItem == newItem
    override fun onMoved(fromPosition: Int, toPosition: Int) { }
    override fun onChanged(position: Int, count: Int) { }
    override fun onRemoved(position: Int, count: Int) { }
    override fun areItemsTheSame(item1: T?, item2: T?): Boolean = item1 == item2
    override fun compare(o1: T?, o2: T?): Int =
            if (o1 == null) if (o2 == null) 0 else 1 else if (o2 == null) -1 else compareNonNull(o1, o2)
    abstract fun compareNonNull(o1: T, o2: T): Int
}

object URLSorter : BaseSorter<URL>() {
    private val ordering = compareBy<URL>({ it.host }, { it.port }, { it.file }, { it.protocol })
    override fun compareNonNull(o1: URL, o2: URL): Int = ordering.compare(o1, o2)
}
