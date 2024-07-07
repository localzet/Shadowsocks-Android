package com.localzet.shadowsocks.plugin

import com.localzet.shadowsocks.Core.app

object NoPlugin : Plugin() {
    override val id: String get() = ""
    override val label: CharSequence get() = app.getText(com.localzet.shadowsocks.core.R.string.plugin_disabled)
}
