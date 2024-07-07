

package com.github.shadowsocks

import android.app.KeyguardManager
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import com.github.shadowsocks.core.R
import com.github.shadowsocks.preference.DataStore
import com.github.shadowsocks.utils.Key
import com.github.shadowsocks.utils.StartService
import com.github.shadowsocks.utils.broadcastReceiver

class VpnRequestActivity : AppCompatActivity() {
    private var receiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (DataStore.serviceMode != Key.modeVpn) {
            finish()
            return
        }
        if (getSystemService<KeyguardManager>()!!.isKeyguardLocked) {
            receiver = broadcastReceiver { _, _ -> connect.launch(null) }
            registerReceiver(receiver, IntentFilter(Intent.ACTION_USER_PRESENT))
        } else connect.launch(null)
    }

    private val connect = registerForActivityResult(StartService()) {
        if (it) Toast.makeText(this, R.string.vpn_permission_denied, Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (receiver != null) unregisterReceiver(receiver)
    }
}
