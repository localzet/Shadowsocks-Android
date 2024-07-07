

package com.github.shadowsocks.bg

import android.app.KeyguardManager
import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import com.github.shadowsocks.Core
import com.github.shadowsocks.R
import com.github.shadowsocks.aidl.IShadowsocksService
import com.github.shadowsocks.aidl.ShadowsocksConnection
import com.github.shadowsocks.preference.DataStore
import android.service.quicksettings.TileService as BaseTileService

@RequiresApi(24)
class TileService : BaseTileService(), ShadowsocksConnection.Callback {
    private val iconIdle by lazy { Icon.createWithResource(this, R.drawable.ic_service_idle) }
    private val iconBusy by lazy { Icon.createWithResource(this, R.drawable.ic_service_busy) }
    private val iconConnected by lazy { Icon.createWithResource(this, R.drawable.ic_service_active) }
    private val keyguard by lazy { getSystemService<KeyguardManager>()!! }
    private var tapPending = false

    private val connection = ShadowsocksConnection()
    override fun stateChanged(state: BaseService.State, profileName: String?, msg: String?) =
            updateTile(state) { profileName }
    override fun onServiceConnected(service: IShadowsocksService) {
        updateTile(BaseService.State.values()[service.state]) { service.profileName }
        if (tapPending) {
            tapPending = false
            toggle()
        }
    }

    override fun onStartListening() {
        super.onStartListening()
        connection.connect(this, this)
    }
    override fun onStopListening() {
        connection.disconnect(this)
        super.onStopListening()
    }

    override fun onClick() {
        if (isLocked && !DataStore.canToggleLocked) unlockAndRun(this::toggle) else toggle()
    }

    private fun updateTile(serviceState: BaseService.State, profileName: () -> String?) {
        qsTile?.apply {
            label = null
            when (serviceState) {
                BaseService.State.Idle -> error("serviceState")
                BaseService.State.Connecting -> {
                    icon = iconBusy
                    state = Tile.STATE_ACTIVE
                }
                BaseService.State.Connected -> {
                    icon = iconConnected
                    if (!keyguard.isDeviceLocked) label = profileName()
                    state = Tile.STATE_ACTIVE
                }
                BaseService.State.Stopping -> {
                    icon = iconBusy
                    state = Tile.STATE_UNAVAILABLE
                }
                BaseService.State.Stopped -> {
                    icon = iconIdle
                    state = Tile.STATE_INACTIVE
                }
            }
            label = label ?: getString(R.string.app_name)
            updateTile()
        }
    }

    private fun toggle() {
        val service = connection.service
        if (service == null) tapPending = true else BaseService.State.values()[service.state].let { state ->
            when {
                state.canStop -> Core.stopService()
                state == BaseService.State.Stopped -> Core.startService()
            }
        }
    }
}
