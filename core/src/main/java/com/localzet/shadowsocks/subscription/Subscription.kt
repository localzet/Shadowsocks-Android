

package com.localzet.shadowsocks.subscription

import androidx.recyclerview.widget.SortedList
import com.localzet.shadowsocks.preference.DataStore
import com.localzet.shadowsocks.utils.URLSorter
import com.localzet.shadowsocks.utils.asIterable
import java.io.Reader
import java.net.URL

class Subscription {
    companion object {
        private const val SUBSCRIPTION = "subscription"

        var instance: Subscription
            get() {
                val sub = Subscription()
                val str = DataStore.publicStore.getString(SUBSCRIPTION)
                if (str != null) sub.fromReader(str.reader())
                return sub
            }
            set(value) = DataStore.publicStore.putString(SUBSCRIPTION, value.toString())
    }

    val urls = SortedList(URL::class.java, URLSorter)

    fun fromReader(reader: Reader): Subscription {
        urls.clear()
        reader.useLines {
            for (line in it) try {
                urls.add(URL(line))
            } catch (_: Exception) { }
        }
        return this
    }

    override fun toString(): String {
        val result = StringBuilder()
        result.append(urls.asIterable().joinToString("\n"))
        return result.toString()
    }
}
