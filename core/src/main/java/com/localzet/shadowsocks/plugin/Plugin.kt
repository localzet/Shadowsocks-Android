

package com.localzet.shadowsocks.plugin

import android.graphics.drawable.Drawable

abstract class Plugin {
    abstract val id: String
    open val idAliases get() = emptyArray<String>()
    abstract val label: CharSequence
    open val icon: Drawable? get() = null
    open val defaultConfig: String? get() = null
    open val packageName: String get() = ""
    open val trusted: Boolean get() = true
    open val directBootAware: Boolean get() = true

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return id == (other as Plugin).id
    }
    override fun hashCode() = id.hashCode()
}
