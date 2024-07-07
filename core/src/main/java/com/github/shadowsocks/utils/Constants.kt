

package com.github.shadowsocks.utils

object Key {
    /**
     * Public config that doesn't need to be kept secret.
     */
    const val DB_PUBLIC = "config.db"
    const val DB_PROFILE = "profile.db"

    const val id = "profileId"
    const val name = "profileName"

    const val individual = "Proxyed"

    const val serviceMode = "serviceMode"
    const val modeProxy = "proxy"
    const val modeVpn = "vpn"
    const val modeTransproxy = "transproxy"
    const val shareOverLan = "shareOverLan"
    const val portProxy = "portProxy"
    const val portLocalDns = "portLocalDns"
    const val portTransproxy = "portTransproxy"

    const val route = "route"

    const val persistAcrossReboot = "isAutoConnect"
    const val directBootAware = "directBootAware"

    const val proxyApps = "isProxyApps"
    const val bypass = "isBypassApps"
    const val udpdns = "isUdpDns"
    const val ipv6 = "isIpv6"
    const val metered = "metered"

    const val host = "proxy"
    const val password = "sitekey"
    const val method = "encMethod"
    const val remotePort = "remotePortNum"
    const val remoteDns = "remoteDns"

    const val plugin = "plugin"
    const val pluginConfigure = "plugin.configure"
    const val udpFallback = "udpFallback"

    const val dirty = "profileDirty"

    const val assetUpdateTime = "assetUpdateTime"

    // TV specific values
    const val controlStats = "control.stats"
    const val controlImport = "control.import"
    const val controlExport = "control.export"
    const val about = "about"
    const val aboutOss = "about.ossLicenses"
}

object Action {
    const val SERVICE = "com.github.shadowsocks.SERVICE"
    const val CLOSE = "com.github.shadowsocks.CLOSE"
    const val RELOAD = "com.github.shadowsocks.RELOAD"
    const val ABORT = "com.github.shadowsocks.ABORT"

    const val EXTRA_PROFILE_ID = "com.github.shadowsocks.EXTRA_PROFILE_ID"
}
