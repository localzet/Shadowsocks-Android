

package com.localzet.shadowsocks.plugin

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import androidx.core.os.bundleOf

/**
 * Base class for a native plugin provider. A native plugin provider offers read-only access to files that are required
 * to run a plugin, such as binary files and other configuration files. To create a native plugin provider, extend this
 * class, implement the abstract methods, and add it to your manifest like this:
 *
 * <pre class="prettyprint">&lt;manifest&gt;
 *    ...
 *    &lt;application&gt;
 *        ...
 *        &lt;provider android:name="com.localzet.shadowsocks.$PLUGIN_ID.BinaryProvider"
 *                     android:authorities="com.localzet.shadowsocks.plugin.$PLUGIN_ID.BinaryProvider"&gt;
 *            &lt;intent-filter&gt;
 *                &lt;category android:name="com.localzet.shadowsocks.plugin.ACTION_NATIVE_PLUGIN" /&gt;
 *            &lt;/intent-filter&gt;
 *        &lt;/provider&gt;
 *        ...
 *    &lt;/application&gt;
 *&lt;/manifest&gt;</pre>
 */
abstract class NativePluginProvider : ContentProvider() {
    override fun getType(uri: Uri): String? = "application/x-elf"

    override fun onCreate(): Boolean = true

    /**
     * Provide all files needed for native plugin.
     *
     * @param provider A helper object to use to add files.
     */
    protected abstract fun populateFiles(provider: PathProvider)

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?,
                       sortOrder: String?): Cursor? {
        check(selection == null && selectionArgs == null && sortOrder == null)
        val result = MatrixCursor(projection)
        populateFiles(PathProvider(uri, result))
        return result
    }

    /**
     * Returns executable entry absolute path.
     * This is used for fast mode initialization where ss-local launches your native binary at the path given directly.
     * In order for this to work, plugin app is encouraged to have the following in its AndroidManifest.xml:
     *  - android:installLocation="internalOnly" for <manifest>
     *  - android:extractNativeLibs="true" for <application>
     *
     * Default behavior is throwing UnsupportedOperationException. If you don't wish to use this feature, use the
     * default behavior.
     *
     * @return Absolute path for executable entry.
     */
    open fun getExecutable(): String = throw UnsupportedOperationException()

    abstract fun openFile(uri: Uri): ParcelFileDescriptor
    override fun openFile(uri: Uri, mode: String): ParcelFileDescriptor {
        check(mode == "r")
        return openFile(uri)
    }

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle? = when (method) {
        PluginContract.METHOD_GET_EXECUTABLE -> bundleOf(Pair(PluginContract.EXTRA_ENTRY, getExecutable()))
        else -> super.call(method, arg, extras)
    }

    // Methods that should not be used
    override fun insert(uri: Uri, values: ContentValues?): Uri? = throw UnsupportedOperationException()
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int =
            throw UnsupportedOperationException()
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int =
            throw UnsupportedOperationException()
}
