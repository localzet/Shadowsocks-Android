

package com.localzet.shadowsocks.bg

import android.app.Service
import android.content.Intent

/**
 * Shadowsocks service at its minimum.
 */
class ProxyService : Service(), BaseService.Interface {
    override val data = BaseService.Data(this)
    override val tag: String get() = "ShadowsocksProxyService"
    override fun createNotification(profileName: String): ServiceNotification =
            ServiceNotification(this, profileName, "service-proxy", true)

    override fun onBind(intent: Intent) = super.onBind(intent)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int =
            super<BaseService.Interface>.onStartCommand(intent, flags, startId)
    override fun onDestroy() {
        super.onDestroy()
        data.binder.close()
    }
}
