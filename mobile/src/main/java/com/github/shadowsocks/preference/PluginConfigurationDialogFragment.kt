

package com.github.shadowsocks.preference

import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.preference.EditTextPreferenceDialogFragmentCompat
import androidx.preference.PreferenceDialogFragmentCompat
import com.github.shadowsocks.ProfileConfigActivity
import com.github.shadowsocks.plugin.PluginContract
import com.github.shadowsocks.plugin.PluginManager

class PluginConfigurationDialogFragment : EditTextPreferenceDialogFragmentCompat() {
    companion object {
        private const val PLUGIN_ID_FRAGMENT_TAG =
                "com.github.shadowsocks.preference.PluginConfigurationDialogFragment.PLUGIN_ID"
    }

    fun setArg(key: String, plugin: String) {
        arguments = bundleOf(PreferenceDialogFragmentCompat.ARG_KEY to key, PLUGIN_ID_FRAGMENT_TAG to plugin)
    }

    private lateinit var editText: EditText

    override fun onPrepareDialogBuilder(builder: AlertDialog.Builder) {
        super.onPrepareDialogBuilder(builder)
        val intent = PluginManager.buildIntent(arguments?.getString(PLUGIN_ID_FRAGMENT_TAG)!!,
                PluginContract.ACTION_HELP)
        val activity = activity as ProfileConfigActivity
        if (intent.resolveActivity(activity.packageManager) != null) builder.setNeutralButton("?") { _, _ ->
            activity.pluginHelp.launch(intent.putExtra(PluginContract.EXTRA_OPTIONS, editText.text.toString()))
        }
    }

    override fun onBindDialogView(view: View) {
        super.onBindDialogView(view)
        editText = view.findViewById(android.R.id.edit)
    }
}
