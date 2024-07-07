

package com.localzet.shadowsocks.plugin

import com.localzet.shadowsocks.utils.Commandline
import timber.log.Timber
import java.util.*

class PluginConfiguration(val pluginsOptions: Map<String, PluginOptions>, val selected: String) {
    private constructor(plugins: List<PluginOptions>) : this(
            plugins.filter { it.id.isNotEmpty() }.associateBy { it.id },
            if (plugins.isEmpty()) "" else plugins[0].id)
    constructor(plugin: String) : this(plugin.split('\n').map { line ->
        if (line.startsWith("kcptun ")) {
            val opt = PluginOptions()
            opt.id = "kcptun"
            try {
                val iterator = Commandline.translateCommandline(line).drop(1).iterator()
                while (iterator.hasNext()) {
                    val option = iterator.next()
                    when {
                        option == "--nocomp" -> opt["nocomp"] = null
                        option.startsWith("--") -> opt[option.substring(2)] = iterator.next()
                        else -> throw IllegalArgumentException("Unknown kcptun parameter: $option")
                    }
                }
            } catch (exc: Exception) {
                Timber.w(exc)
            }
            opt
        } else PluginOptions(line)
    })

    fun getOptions(
            id: String = selected,
            defaultConfig: () -> String? = { PluginManager.fetchPlugins().lookup[id]?.defaultConfig }
    ) = if (id.isEmpty()) PluginOptions() else pluginsOptions[id] ?: PluginOptions(id, defaultConfig())

    override fun toString(): String {
        val result = LinkedList<PluginOptions>()
        for ((id, opt) in pluginsOptions) if (id == this.selected) result.addFirst(opt) else result.addLast(opt)
        if (!pluginsOptions.contains(selected)) result.addFirst(getOptions())
        return result.joinToString("\n") { it.toString(false) }
    }
}
