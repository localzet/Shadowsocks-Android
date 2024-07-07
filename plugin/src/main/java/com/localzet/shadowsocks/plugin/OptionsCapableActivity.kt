

package com.localzet.shadowsocks.plugin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity that's capable of getting EXTRA_OPTIONS input.
 */
abstract class OptionsCapableActivity : AppCompatActivity() {
    protected fun pluginOptions(intent: Intent = this.intent) = try {
        PluginOptions("", intent.getStringExtra(PluginContract.EXTRA_OPTIONS))
    } catch (exc: IllegalArgumentException) {
        Toast.makeText(this, exc.message, Toast.LENGTH_SHORT).show()
        PluginOptions()
    }

    /**
     * Populate args to your user interface.
     *
     * @param options PluginOptions parsed.
     */
    protected abstract fun onInitializePluginOptions(options: PluginOptions = pluginOptions())

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (savedInstanceState == null) onInitializePluginOptions()
    }
}
