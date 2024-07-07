

package com.localzet.shadowsocks

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.localzet.shadowsocks.bg.BaseService
import com.localzet.shadowsocks.preference.DataStore
import com.localzet.shadowsocks.preference.EditTextPreferenceModifiers
import com.localzet.shadowsocks.utils.DirectBoot
import com.localzet.shadowsocks.utils.Key
import com.localzet.shadowsocks.utils.remove
import com.localzet.shadowsocks.widget.MainListListener

class GlobalSettingsPreferenceFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore = DataStore.publicStore
        DataStore.initGlobal()
        addPreferencesFromResource(R.xml.pref_global)
        findPreference<SwitchPreference>(Key.persistAcrossReboot)!!.setOnPreferenceChangeListener { _, value ->
            BootReceiver.enabled = value as Boolean
            true
        }

        val canToggleLocked = findPreference<Preference>(Key.directBootAware)!!
        if (Build.VERSION.SDK_INT >= 24) canToggleLocked.setOnPreferenceChangeListener { _, newValue ->
            if (Core.directBootSupported && newValue as Boolean) DirectBoot.update() else DirectBoot.clean()
            true
        } else canToggleLocked.remove()

        val serviceMode = findPreference<Preference>(Key.serviceMode)!!
        val portProxy = findPreference<EditTextPreference>(Key.portProxy)!!
        portProxy.setOnBindEditTextListener(EditTextPreferenceModifiers.Port)
        val portLocalDns = findPreference<EditTextPreference>(Key.portLocalDns)!!
        portLocalDns.setOnBindEditTextListener(EditTextPreferenceModifiers.Port)
        val portTransproxy = findPreference<EditTextPreference>(Key.portTransproxy)!!
        portTransproxy.setOnBindEditTextListener(EditTextPreferenceModifiers.Port)
        val onServiceModeChange = Preference.OnPreferenceChangeListener { _, newValue ->
            portTransproxy.isEnabled = newValue as String? == Key.modeTransproxy
            true
        }
        val listener: (BaseService.State) -> Unit = {
            val stopped = it == BaseService.State.Stopped
            serviceMode.isEnabled = stopped
            portProxy.isEnabled = stopped
            portLocalDns.isEnabled = stopped
            if (stopped) onServiceModeChange.onPreferenceChange(serviceMode, DataStore.serviceMode) else {
                portTransproxy.isEnabled = false
            }
        }
        listener((activity as MainActivity).state)
        MainActivity.stateListener = listener
        serviceMode.onPreferenceChangeListener = onServiceModeChange
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(listView, MainListListener)
    }

    override fun onDestroy() {
        MainActivity.stateListener = null
        super.onDestroy()
    }
}
