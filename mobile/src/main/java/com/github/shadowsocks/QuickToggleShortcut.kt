

package com.github.shadowsocks

import android.app.Activity
import android.content.Intent
import android.content.pm.ShortcutManager
import android.os.Build
import android.os.Bundle
import androidx.core.content.getSystemService
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.github.shadowsocks.aidl.IShadowsocksService
import com.github.shadowsocks.aidl.ShadowsocksConnection
import com.github.shadowsocks.bg.BaseService

@Suppress("DEPRECATION")
@Deprecated("This shortcut is inefficient and should be superseded by TileService for API 24+.")
class QuickToggleShortcut : Activity(), ShadowsocksConnection.Callback {
    private val connection = ShadowsocksConnection()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.action == Intent.ACTION_CREATE_SHORTCUT) {
            setResult(RESULT_OK, ShortcutManagerCompat.createShortcutResultIntent(this,
                    ShortcutInfoCompat.Builder(this, "toggle")
                            .setIntent(Intent(this, QuickToggleShortcut::class.java).setAction(Intent.ACTION_MAIN))
                            .setIcon(IconCompat.createWithResource(this, R.drawable.ic_qu_shadowsocks_launcher))
                            .setShortLabel(getString(R.string.quick_toggle))
                            .build()))
            finish()
        } else {
            connection.connect(this, this)
            if (Build.VERSION.SDK_INT >= 25) getSystemService<ShortcutManager>()!!.reportShortcutUsed("toggle")
        }
    }

    override fun onServiceConnected(service: IShadowsocksService) {
        val state = BaseService.State.values()[service.state]
        when {
            state.canStop -> Core.stopService()
            state == BaseService.State.Stopped -> Core.startService()
        }
        finish()
    }

    override fun stateChanged(state: BaseService.State, profileName: String?, msg: String?) { }

    override fun onDestroy() {
        connection.disconnect(this)
        super.onDestroy()
    }
}
