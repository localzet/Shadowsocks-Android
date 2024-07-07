

package com.github.shadowsocks.tv.preference

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.leanback.preference.LeanbackListPreferenceDialogFragmentCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * Fix: scroll to selected item.
 */
open class LeanbackSingleListPreferenceDialogFragment : LeanbackListPreferenceDialogFragmentCompat() {
    companion object {
        private val mEntryValues = LeanbackListPreferenceDialogFragmentCompat::class.java
                .getDeclaredField("mEntryValues").apply { isAccessible = true }
        private val mInitialSelection = LeanbackListPreferenceDialogFragmentCompat::class.java
                .getDeclaredField("mInitialSelection").apply { isAccessible = true }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val selected = mInitialSelection.get(this) as? String
        val index = (mEntryValues.get(this) as? Array<*>)?.indexOfFirst { selected == it }
        return super.onCreateView(inflater, container, savedInstanceState)!!.also {
            if (index != null) it.findViewById<RecyclerView>(android.R.id.list).layoutManager!!.scrollToPosition(index)
        }
    }
}
