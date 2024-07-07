

@file:JvmName("Utils")

package com.localzet.shadowsocks.plugin.fragment

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

typealias Empty = com.localzet.shadowsocks.plugin.Empty

@JvmOverloads
fun DialogFragment.showAllowingStateLoss(fragmentManager: FragmentManager, tag: String? = null) {
    if (!fragmentManager.isStateSaved) show(fragmentManager, tag)
}
