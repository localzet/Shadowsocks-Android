

package com.localzet.shadowsocks.bg

import android.app.Service
import android.content.Intent
import com.localzet.shadowsocks.Core
import com.localzet.shadowsocks.preference.DataStore
import java.io.File

class TransproxyService : Service(), BaseService.Interface {
    override val data = BaseService.Data(this)
    override val tag: String get() = "ShadowsocksTransproxyService"
    override fun createNotification(profileName: String): ServiceNotification =
            ServiceNotification(this, profileName, "service-transproxy", true)

    override fun onBind(intent: Intent) = super.onBind(intent)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int =
            super<BaseService.Interface>.onStartCommand(intent, flags, startId)

    private fun startRedsocksDaemon() {
        File(Core.deviceStorage.noBackupFilesDir, "redsocks.conf").writeText("""base {
 log_debug = off;
 log_info = off;
 log = stderr;
 daemon = off;
 redirector = iptables;
}
redsocks {
 local_ip = ${DataStore.listenAddress};
 local_port = ${DataStore.portTransproxy};
 ip = 127.0.0.1;
 port = ${DataStore.portProxy};
 type = socks5;
}
""")
        data.processes!!.start(listOf(
                File(applicationInfo.nativeLibraryDir, Executable.REDSOCKS).absolutePath, "-c", "redsocks.conf"))
    }

    override suspend fun startProcesses() {
        startRedsocksDaemon()
        super.startProcesses()
    }

    override fun onDestroy() {
        super.onDestroy()
        data.binder.close()
    }
}
