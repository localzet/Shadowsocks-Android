

package com.localzet.shadowsocks.plugin

import android.content.Intent

/**
 * HelpCallback is an HelpActivity but you just need to produce a CharSequence help message instead of having to
 * provide UI. To create a help callback, just extend this class, implement abstract methods, and add it to your
 * manifest following the same procedure as adding a HelpActivity.
 */
abstract class HelpCallback : HelpActivity() {
    abstract fun produceHelpMessage(options: PluginOptions): CharSequence

    override fun onInitializePluginOptions(options: PluginOptions) {
        setResult(RESULT_OK, Intent().putExtra(PluginContract.EXTRA_HELP_MESSAGE, produceHelpMessage(options)))
        finish()
    }
}
