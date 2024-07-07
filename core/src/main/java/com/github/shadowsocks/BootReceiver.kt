

package com.github.shadowsocks

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import com.github.shadowsocks.Core.app
import com.github.shadowsocks.preference.DataStore

class BootReceiver : BroadcastReceiver() {
    companion object {
        private val componentName by lazy { ComponentName(app, BootReceiver::class.java) }
        var enabled: Boolean
            get() = app.packageManager.getComponentEnabledSetting(componentName) ==
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED
            set(value) = app.packageManager.setComponentEnabledSetting(componentName,
                    if (value) PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                    else PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (!DataStore.persistAcrossReboot) {   // sanity check
            enabled = false
            return
        }
        val doStart = when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> !DataStore.directBootAware
            Intent.ACTION_LOCKED_BOOT_COMPLETED -> DataStore.directBootAware
            else -> DataStore.directBootAware || Build.VERSION.SDK_INT >= 24 && Core.user.isUserUnlocked
        }
        if (doStart) Core.startService()
    }
}
