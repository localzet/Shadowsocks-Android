

package com.github.shadowsocks

import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.github.shadowsocks.core.R
import com.github.shadowsocks.database.Profile
import com.github.shadowsocks.database.ProfileManager
import com.github.shadowsocks.plugin.fragment.AlertDialogFragment
import com.github.shadowsocks.plugin.fragment.Empty
import com.github.shadowsocks.plugin.fragment.showAllowingStateLoss
import kotlinx.parcelize.Parcelize

class UrlImportActivity : AppCompatActivity() {
    @Parcelize
    data class ProfilesArg(val profiles: List<Profile>) : Parcelable
    class ImportProfilesDialogFragment : AlertDialogFragment<ProfilesArg, Empty>() {
        override fun AlertDialog.Builder.prepare(listener: DialogInterface.OnClickListener) {
            setTitle(R.string.add_profile_dialog)
            setPositiveButton(R.string.yes, listener)
            setNegativeButton(R.string.no, listener)
            setMessage(arg.profiles.joinToString("\n"))
        }

        override fun onClick(dialog: DialogInterface?, which: Int) {
            if (which == DialogInterface.BUTTON_POSITIVE) arg.profiles.forEach { ProfileManager.createProfile(it) }
            requireActivity().finish()
        }

        override fun onDismiss(dialog: DialogInterface) {
            requireActivity().finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (val dialog = handleShareIntent()) {
            null -> {
                Toast.makeText(this, R.string.profile_invalid_input, Toast.LENGTH_SHORT).show()
                finish()
            }
            else -> dialog.showAllowingStateLoss(supportFragmentManager)
        }
    }

    private fun handleShareIntent() = intent.data?.toString()?.let { sharedStr ->
        val profiles = Profile.findAllUrls(sharedStr, Core.currentProfile?.main).toList()
        if (profiles.isEmpty()) null else ImportProfilesDialogFragment().apply {
            arg(ProfilesArg(profiles))
            key()
        }
    }
}
