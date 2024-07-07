

package com.localzet.shadowsocks.tasker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import com.localzet.shadowsocks.R
import com.localzet.shadowsocks.database.ProfileManager
import com.twofortyfouram.locale.api.Intent as ApiIntent

class Settings(bundle: Bundle?) {
    companion object {
        private const val KEY_SWITCH_ON = "switch_on"
        private const val KEY_PROFILE_ID = "profile_id"

        fun fromIntent(intent: Intent) = Settings(intent.getBundleExtra(ApiIntent.EXTRA_BUNDLE))
    }

    var switchOn: Boolean = bundle?.getBoolean(KEY_SWITCH_ON, true) ?: true
    var profileId: Long

    init {
        profileId = bundle?.getLong(KEY_PROFILE_ID, -1L) ?: -1L
        if (profileId < 0) profileId = (bundle?.getInt(KEY_PROFILE_ID, -1) ?: -1).toLong()
    }

    fun toIntent(context: Context): Intent {
        val profile = ProfileManager.getProfile(profileId)
        return Intent()
                .putExtra(ApiIntent.EXTRA_BUNDLE, bundleOf(Pair(KEY_SWITCH_ON, switchOn),
                        Pair(KEY_PROFILE_ID, profileId)))
                .putExtra(ApiIntent.EXTRA_STRING_BLURB,
                        if (profile != null) context.getString(
                                if (switchOn) R.string.start_service else R.string.stop_service, profile.formattedName)
                        else context.getString(if (switchOn) R.string.start_service_default else R.string.stop))
    }
}
