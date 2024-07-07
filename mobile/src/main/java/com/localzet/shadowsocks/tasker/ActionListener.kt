

package com.localzet.shadowsocks.tasker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.localzet.shadowsocks.Core
import com.localzet.shadowsocks.database.ProfileManager

class ActionListener : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val settings = Settings.fromIntent(intent)
        var changed = false
        if (ProfileManager.getProfile(settings.profileId) != null) {
            Core.switchProfile(settings.profileId)
            changed = true
        }
        if (settings.switchOn) {
            Core.startService()
            if (changed) Core.reloadService()
        } else Core.stopService()
    }
}
