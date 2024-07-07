

package com.localzet.shadowsocks.plugin

import android.app.Activity
import android.content.Intent

/**
 * Base class for configuration activity. A configuration activity is started when user wishes to configure the
 * selected plugin. To create a configuration activity, extend this class, implement abstract methods, invoke
 * `saveChanges(options)` and `discardChanges()` when appropriate, and add it to your manifest like this:
 *
 * <pre class="prettyprint">&lt;manifest&gt;
 *    ...
 *    &lt;application&gt;
 *        ...
 *        &lt;activity android:name=".ConfigureActivity"&gt;
 *            &lt;intent-filter&gt;
 *                &lt;action android:name="com.localzet.shadowsocks.plugin.ACTION_CONFIGURE"/&gt;
 *                &lt;category android:name="android.intent.category.DEFAULT"/&gt;
 *                &lt;data android:scheme="plugin"
 *                         android:host="com.localzet.shadowsocks"
 *                         android:path="/$PLUGIN_ID"/&gt;
 *            &lt;/intent-filter&gt;
 *        &lt;/activity&gt;
 *        ...
 *    &lt;/application&gt;
 *&lt;/manifest&gt;</pre>
 */
abstract class ConfigurationActivity : OptionsCapableActivity() {
    /**
     * Equivalent to setResult(RESULT_CANCELED).
     */
    fun discardChanges() = setResult(Activity.RESULT_CANCELED)

    /**
     * Equivalent to setResult(RESULT_OK, args_with_correct_format).
     *
     * @param options PluginOptions to save.
     */
    fun saveChanges(options: PluginOptions) =
            setResult(Activity.RESULT_OK, Intent().putExtra(PluginContract.EXTRA_OPTIONS, options.toString()))

    /**
     * Finish this activity and request manual editor to pop up instead.
     */
    fun fallbackToManualEditor() {
        setResult(PluginContract.RESULT_FALLBACK)
        finish()
    }
}
