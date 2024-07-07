

package com.localzet.shadowsocks.plugin

/**
 * The contract between the plugin provider and host. Contains definitions for the supported actions, extras, etc.
 *
 * This class is written in Java to keep Java interoperability.
 */
object PluginContract {
    /**
     * ContentProvider Action: Used for NativePluginProvider.
     *
     * Constant Value: "com.localzet.shadowsocks.plugin.ACTION_NATIVE_PLUGIN"
     */
    const val ACTION_NATIVE_PLUGIN = "com.localzet.shadowsocks.plugin.ACTION_NATIVE_PLUGIN"

    /**
     * Activity Action: Used for ConfigurationActivity.
     *
     * Constant Value: "com.localzet.shadowsocks.plugin.ACTION_CONFIGURE"
     */
    const val ACTION_CONFIGURE = "com.localzet.shadowsocks.plugin.ACTION_CONFIGURE"
    /**
     * Activity Action: Used for HelpActivity or HelpCallback.
     *
     * Constant Value: "com.localzet.shadowsocks.plugin.ACTION_HELP"
     */
    const val ACTION_HELP = "com.localzet.shadowsocks.plugin.ACTION_HELP"

    /**
     * The lookup key for a string that provides the plugin entry binary.
     *
     * Example: "/data/data/com.localzet.shadowsocks.plugin.obfs_local/lib/libobfs-local.so"
     *
     * Constant Value: "com.localzet.shadowsocks.plugin.EXTRA_ENTRY"
     */
    const val EXTRA_ENTRY = "com.localzet.shadowsocks.plugin.EXTRA_ENTRY"
    /**
     * The lookup key for a string that provides the options as a string.
     *
     * Example: "obfs=http;obfs-host=www.baidu.com"
     *
     * Constant Value: "com.localzet.shadowsocks.plugin.EXTRA_OPTIONS"
     */
    const val EXTRA_OPTIONS = "com.localzet.shadowsocks.plugin.EXTRA_OPTIONS"
    /**
     * The lookup key for a CharSequence that provides user relevant help message.
     *
     * Example: "obfs=<http></http>|tls>            Enable obfuscating: HTTP or TLS (Experimental).
     * obfs-host=<host_name>      Hostname for obfuscating (Experimental)."
     *
     * Constant Value: "com.localzet.shadowsocks.plugin.EXTRA_HELP_MESSAGE"
    </host_name> */
    const val EXTRA_HELP_MESSAGE = "com.localzet.shadowsocks.plugin.EXTRA_HELP_MESSAGE"

    /**
     * The metadata key to retrieve plugin version. Required for plugin applications.
     *
     * Constant Value: "com.localzet.shadowsocks.plugin.version"
     */
    const val METADATA_KEY_VERSION = "com.localzet.shadowsocks.plugin.version"

    /**
     * The metadata key to retrieve plugin id. Required for plugins.
     *
     * Constant Value: "com.localzet.shadowsocks.plugin.id"
     */
    const val METADATA_KEY_ID = "com.localzet.shadowsocks.plugin.id"
    /**
     * The metadata key to retrieve plugin id aliases.
     * Can be a string (representing one alias) or a resource to a string or string array.
     *
     * Constant Value: "com.localzet.shadowsocks.plugin.id.aliases"
     */
    const val METADATA_KEY_ID_ALIASES = "com.localzet.shadowsocks.plugin.id.aliases"
    /**
     * The metadata key to retrieve default configuration. Default value is empty.
     *
     * Constant Value: "com.localzet.shadowsocks.plugin.default_config"
     */
    const val METADATA_KEY_DEFAULT_CONFIG = "com.localzet.shadowsocks.plugin.default_config"
    /**
     * The metadata key to retrieve executable path to your native binary.
     * This path should be relative to your application's nativeLibraryDir.
     *
     * If this is set, the host app will prefer this value and (probably) not launch your app at all (aka faster mode).
     * In order for this to work, plugin app is encouraged to have the following in its AndroidManifest.xml:
     *  - android:installLocation="internalOnly" for <manifest>
     *  - android:extractNativeLibs="true" for <application>
     *
     * Do not use this if you plan to do some setup work before giving away your binary path,
     *  or your native binary is not at a fixed location relative to your application's nativeLibraryDir.
     *
     * Since plugin lib: 1.3.0
     *
     * Constant Value: "com.localzet.shadowsocks.plugin.executable_path"
     */
    const val METADATA_KEY_EXECUTABLE_PATH = "com.localzet.shadowsocks.plugin.executable_path"

    const val METHOD_GET_EXECUTABLE = "shadowsocks:getExecutable"

    /** ConfigurationActivity result: fallback to manual edit mode.  */
    const val RESULT_FALLBACK = 1

    /**
     * Relative to the file to be copied. This column is required.
     *
     * Example: "kcptun", "doc/help.txt"
     *
     * Type: String
     */
    const val COLUMN_PATH = "path"
    /**
     * File mode bits. Default value is 644 in octal.
     *
     * Example: 0b110100100 (for 755 in octal)
     *
     * Type: Int or String (deprecated)
     */
    const val COLUMN_MODE = "mode"

    /**
     * The scheme for general plugin actions.
     */
    const val SCHEME = "plugin"
    /**
     * The authority for general plugin actions.
     */
    const val AUTHORITY = "com.localzet.shadowsocks"
}
