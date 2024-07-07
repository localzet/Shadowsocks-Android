

package com.localzet.shadowsocks.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.VpnService
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.localzet.shadowsocks.Core
import com.localzet.shadowsocks.preference.DataStore
import timber.log.Timber

private val jsonMimeTypes = arrayOf("application/*", "text/*")

object OpenJson : ActivityResultContracts.GetMultipleContents() {
    override fun createIntent(context: Context, input: String) = super.createIntent(context,
            jsonMimeTypes.first()).apply { putExtra(Intent.EXTRA_MIME_TYPES, jsonMimeTypes) }
}

object SaveJson : ActivityResultContracts.CreateDocument("application/json") {
    override fun createIntent(context: Context, input: String) =
            super.createIntent(context, "profiles.json")
}

class StartService : ActivityResultContract<Void?, Boolean>() {
    private var cachedIntent: Intent? = null

    override fun getSynchronousResult(context: Context, input: Void?): SynchronousResult<Boolean>? {
        if (DataStore.serviceMode == Key.modeVpn) VpnService.prepare(context)?.let { intent ->
            cachedIntent = intent
            return null
        }
        Core.startService()
        return SynchronousResult(false)
    }

    override fun createIntent(context: Context, input: Void?) = cachedIntent!!.also { cachedIntent = null }

    override fun parseResult(resultCode: Int, intent: Intent?) = if (resultCode == Activity.RESULT_OK) {
        Core.startService()
        false
    } else {
        Timber.e("Failed to start VpnService: $intent")
        true
    }
}
