

package com.localzet.shadowsocks.plugin

import android.content.pm.ComponentInfo
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.os.Build
import com.localzet.shadowsocks.Core
import com.localzet.shadowsocks.Core.app
import com.localzet.shadowsocks.plugin.PluginManager.loadString
import com.localzet.shadowsocks.utils.signaturesCompat

abstract class ResolvedPlugin(protected val resolveInfo: ResolveInfo) : Plugin() {
    protected abstract val componentInfo: ComponentInfo

    override val id by lazy { componentInfo.loadString(PluginContract.METADATA_KEY_ID)!! }
    override val idAliases: Array<String> by lazy {
        when (val value = componentInfo.metaData.get(PluginContract.METADATA_KEY_ID_ALIASES)) {
            is String -> arrayOf(value)
            is Int -> app.packageManager.getResourcesForApplication(componentInfo.applicationInfo).run {
                when (getResourceTypeName(value)) {
                    "string" -> arrayOf(getString(value))
                    else -> getStringArray(value)
                }
            }
            null -> emptyArray()
            else -> error("unknown type for plugin meta-data idAliases")
        }
    }
    override val label: CharSequence get() = resolveInfo.loadLabel(app.packageManager)
    override val icon: Drawable get() = resolveInfo.loadIcon(app.packageManager)
    override val defaultConfig by lazy { componentInfo.loadString(PluginContract.METADATA_KEY_DEFAULT_CONFIG) }
    override val packageName: String get() = componentInfo.packageName
    override val trusted by lazy {
        Core.getPackageInfo(packageName).signaturesCompat.any(PluginManager.trustedSignatures::contains)
    }
    override val directBootAware get() = Build.VERSION.SDK_INT < 24 || componentInfo.directBootAware
}
