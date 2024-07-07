

package com.localzet.shadowsocks.plugin

/**
 * Base class for a help activity. A help activity is started when user taps help when configuring options for your
 * plugin. To create a help activity, just extend this class, and add it to your manifest like this:
 *
 * <pre class="prettyprint">&lt;manifest&gt;
 *    ...
 *    &lt;application&gt;
 *        ...
 *        &lt;activity android:name=".HelpActivity"&gt;
 *            &lt;intent-filter&gt;
 *                &lt;action android:name="com.localzet.shadowsocks.plugin.ACTION_HELP"/&gt;
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
abstract class HelpActivity : OptionsCapableActivity() {
    override fun onInitializePluginOptions(options: PluginOptions) { }
}
