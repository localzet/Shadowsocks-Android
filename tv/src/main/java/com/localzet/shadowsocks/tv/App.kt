

package com.localzet.shadowsocks.tv

import android.app.Application
import android.content.res.Configuration
import com.localzet.shadowsocks.Core

class App : Application(), androidx.work.Configuration.Provider by Core {
    override fun onCreate() {
        super.onCreate()
        Core.init(this, MainActivity::class)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Core.updateNotificationChannels()
    }
}
