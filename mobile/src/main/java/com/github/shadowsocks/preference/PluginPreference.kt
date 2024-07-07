

package com.github.shadowsocks.preference

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.preference.ListPreference
import com.github.shadowsocks.R
import com.github.shadowsocks.plugin.PluginList
import com.github.shadowsocks.plugin.PluginManager

class PluginPreference(context: Context, attrs: AttributeSet? = null) : ListPreference(context, attrs) {
    companion object FallbackProvider : SummaryProvider<PluginPreference> {
        override fun provideSummary(preference: PluginPreference) =
                preference.selectedEntry?.label ?: preference.unknownValueSummary.format(preference.value)
    }

    lateinit var plugins: PluginList
    val selectedEntry get() = plugins.lookup[value]
    private val entryIcon: Drawable? get() = selectedEntry?.icon
    private val unknownValueSummary = context.getString(R.string.plugin_unknown)

    private var listener: OnPreferenceChangeListener? = null
    override fun getOnPreferenceChangeListener(): OnPreferenceChangeListener? = listener
    override fun setOnPreferenceChangeListener(listener: OnPreferenceChangeListener?) {
        this.listener = listener
    }

    init {
        super.setOnPreferenceChangeListener { preference, newValue ->
            val listener = listener
            if (listener == null || listener.onPreferenceChange(preference, newValue)) {
                value = newValue.toString()
                icon = entryIcon
                true
            } else false
        }
    }

    fun init() {
        plugins = PluginManager.fetchPlugins()
        entryValues = plugins.lookup.map { it.key }.toTypedArray()
        icon = entryIcon
        summaryProvider = FallbackProvider
    }
    override fun onSetInitialValue(defaultValue: Any?) {
        super.onSetInitialValue(defaultValue)
        init()
    }
}
