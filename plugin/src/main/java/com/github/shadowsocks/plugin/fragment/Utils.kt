

@file:JvmName("Utils")

package com.github.shadowsocks.plugin.fragment

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

typealias Empty = com.github.shadowsocks.plugin.Empty

@JvmOverloads
fun DialogFragment.showAllowingStateLoss(fragmentManager: FragmentManager, tag: String? = null) {
    if (!fragmentManager.isStateSaved) show(fragmentManager, tag)
}
