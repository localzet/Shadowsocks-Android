

package com.github.shadowsocks.plugin

import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import com.github.shadowsocks.Core.app

class PluginList : ArrayList<Plugin>() {
    init {
        add(NoPlugin)
        addAll(app.packageManager.queryIntentContentProviders(
                Intent(PluginContract.ACTION_NATIVE_PLUGIN), PackageManager.GET_META_DATA)
                .filter { it.providerInfo.exported }.map { NativePlugin(it) })
    }

    val lookup = mutableMapOf<String, Plugin>().apply {
        for (plugin in this@PluginList) {
            fun check(old: Plugin?) {
                if (old != null && old !== plugin) {
                    val packages = this@PluginList.filter { it.id == plugin.id }.joinToString { it.packageName }
                    val message = "Conflicting plugins found from: $packages"
                    Toast.makeText(app, message, Toast.LENGTH_LONG).show()
                    throw IllegalStateException(message)
                }
            }
            check(put(plugin.id, plugin))
            for (alias in plugin.idAliases) check(put(alias, plugin))
        }
    }
}
