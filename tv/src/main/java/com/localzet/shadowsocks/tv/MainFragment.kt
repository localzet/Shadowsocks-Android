

package com.localzet.shadowsocks.tv

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.updateLayoutParams
import androidx.leanback.preference.LeanbackPreferenceDialogFragmentCompat
import androidx.leanback.preference.LeanbackSettingsFragmentCompat
import androidx.preference.*
import com.localzet.shadowsocks.bg.BaseService
import com.localzet.shadowsocks.tv.preference.LeanbackSingleListPreferenceDialogFragment
import com.localzet.shadowsocks.utils.Key

class MainFragment : LeanbackSettingsFragmentCompat() {
    override fun onPreferenceStartInitialScreen() = startPreferenceFragment(MainPreferenceFragment())
    override fun onPreferenceStartScreen(caller: PreferenceFragmentCompat, pref: PreferenceScreen): Boolean {
        onPreferenceStartInitialScreen()
        return true
    }
    override fun onPreferenceStartFragment(caller: PreferenceFragmentCompat, pref: Preference): Boolean = false
    override fun onPreferenceDisplayDialog(caller: PreferenceFragmentCompat, pref: Preference): Boolean {
        if (pref.key == Key.id) {
            if ((childFragmentManager.findFragmentById(R.id.settings_preference_fragment_container)
                            as MainPreferenceFragment).state == BaseService.State.Stopped) {
                startPreferenceFragment(ProfilesDialogFragment().apply {
                    arguments = bundleOf(Pair(LeanbackPreferenceDialogFragmentCompat.ARG_KEY, Key.id))
                    setTargetFragment(caller, 0)
                })
            }
            return true
        }
        if (pref is ListPreference && pref !is MultiSelectListPreference) {
            startPreferenceFragment(LeanbackSingleListPreferenceDialogFragment().apply {
                arguments = bundleOf(Pair(LeanbackPreferenceDialogFragmentCompat.ARG_KEY, pref.key))
                setTargetFragment(caller, 0)
            })
            return true
        }
        return super.onPreferenceDisplayDialog(caller, pref)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (BuildConfig.FULLSCREEN) {
            view.findViewById<View>(R.id.settings_preference_fragment_container).updateLayoutParams {
                width = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }
    }
}
