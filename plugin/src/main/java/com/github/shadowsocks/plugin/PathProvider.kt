

package com.github.shadowsocks.plugin

import android.database.MatrixCursor
import android.net.Uri
import java.io.File

/**
 * Helper class to provide relative paths of files to copy.
 */
class PathProvider internal constructor(baseUri: Uri, private val cursor: MatrixCursor) {
    private val basePath = baseUri.path?.trim('/') ?: ""

    fun addPath(path: String, mode: Int = 0b110100100): PathProvider {
        val trimmed = path.trim('/')
        if (trimmed.startsWith(basePath)) cursor.newRow()
                .add(PluginContract.COLUMN_PATH, trimmed)
                .add(PluginContract.COLUMN_MODE, mode)
        return this
    }
    fun addTo(file: File, to: String = "", mode: Int = 0b110100100): PathProvider {
        var sub = to + file.name
        if (basePath.startsWith(sub)) if (file.isDirectory) {
            sub += '/'
            file.listFiles()!!.forEach { addTo(it, sub, mode) }
        } else addPath(sub, mode)
        return this
    }
    fun addAt(file: File, at: String = "", mode: Int = 0b110100100): PathProvider {
        if (basePath.startsWith(at)) {
            if (file.isDirectory) file.listFiles()!!.forEach { addTo(it, at, mode) } else addPath(at, mode)
        }
        return this
    }
}
