

package com.localzet.shadowsocks.tv

import android.os.Bundle
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.TextView
import androidx.fragment.app.setFragmentResult
import androidx.leanback.preference.LeanbackListPreferenceDialogFragmentCompat
import androidx.recyclerview.widget.RecyclerView
import com.localzet.shadowsocks.Core
import com.localzet.shadowsocks.database.ProfileManager
import com.localzet.shadowsocks.plugin.PluginConfiguration
import com.localzet.shadowsocks.preference.DataStore

class ProfilesDialogFragment : LeanbackListPreferenceDialogFragmentCompat() {
    private inner class ProfileViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val widgetView = view.findViewById<CompoundButton>(R.id.button)
        val titleView = view.findViewById<TextView>(android.R.id.title)
        init {
            view.findViewById<ViewGroup>(R.id.container).setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val index = bindingAdapterPosition
            if (index == RecyclerView.NO_POSITION) return
            Core.switchProfile(adapter.profiles[index].id)
            setFragmentResult(ProfilesDialogFragment::class.java.name, Bundle.EMPTY)
            parentFragmentManager.popBackStack()
            adapter.notifyDataSetChanged()
        }
    }
    private inner class ProfilesAdapter : RecyclerView.Adapter<ProfileViewHolder>() {
        val profiles = ProfileManager.getActiveProfiles()!!

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProfileViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.leanback_list_preference_item_single_2,
                        parent, false))

        override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
            val profile = profiles[position]
            holder.widgetView.isChecked = profile.id == DataStore.profileId
            holder.titleView.text = profile.formattedName
            holder.itemView.findViewById<TextView>(android.R.id.summary).text = ArrayList<String>().apply {
                if (!profile.name.isNullOrEmpty()) this += profile.formattedAddress
                val id = PluginConfiguration(profile.plugin ?: "").selected
                if (id.isNotEmpty()) this += getString(R.string.profile_plugin, id)
                if (profile.tx > 0 || profile.rx > 0) this += getString(R.string.traffic,
                        Formatter.formatFileSize(activity, profile.tx), Formatter.formatFileSize(activity, profile.rx))
            }.joinToString("\n")
        }

        override fun getItemCount() = profiles.size
    }

    private val adapter = ProfilesAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return super.onCreateView(inflater, container, savedInstanceState)!!.apply {
            val list = findViewById<RecyclerView>(android.R.id.list)
            list.adapter = adapter
            list.layoutManager!!.scrollToPosition(adapter.profiles.indexOfFirst { it.id == DataStore.profileId })
        }
    }
}
