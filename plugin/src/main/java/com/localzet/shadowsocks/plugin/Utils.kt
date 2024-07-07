

@file:JvmName("Utils")

package com.localzet.shadowsocks.plugin

import android.os.Parcelable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.parcelize.Parcelize

@Parcelize
class Empty : Parcelable

@JvmOverloads
@Deprecated("Moved to fragment package", ReplaceWith("fragment.showAllowingStateLoss"))
fun DialogFragment.showAllowingStateLoss(fragmentManager: FragmentManager, tag: String? = null) {
    if (!fragmentManager.isStateSaved) show(fragmentManager, tag)
}
