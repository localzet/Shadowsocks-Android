

package com.localzet.shadowsocks

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.component1
import androidx.activity.result.component2
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.localzet.shadowsocks.plugin.PluginContract
import com.localzet.shadowsocks.plugin.fragment.AlertDialogFragment
import com.localzet.shadowsocks.plugin.fragment.Empty
import com.localzet.shadowsocks.preference.DataStore
import com.localzet.shadowsocks.widget.ListHolderListener

class ProfileConfigActivity : AppCompatActivity() {
    class UnsavedChangesDialogFragment : AlertDialogFragment<Empty, Empty>() {
        override fun AlertDialog.Builder.prepare(listener: DialogInterface.OnClickListener) {
            setTitle(R.string.unsaved_changes_prompt)
            setPositiveButton(R.string.yes, listener)
            setNegativeButton(R.string.no, listener)
            setNeutralButton(android.R.string.cancel, null)
        }
    }

    private val child by lazy { supportFragmentManager.findFragmentById(R.id.content) as ProfileConfigFragment }
    val unsavedChangesHandler = object : OnBackPressedCallback(DataStore.dirty) {
        override fun handleOnBackPressed() = UnsavedChangesDialogFragment().apply {
            key()
        }.show(supportFragmentManager, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_profile_config)
        ListHolderListener.setup(this)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_navigation_close)
        }
        onBackPressedDispatcher.addCallback(unsavedChangesHandler)
    }

    override fun onResume() {
        super.onResume()
        unsavedChangesHandler.isEnabled = DataStore.dirty
    }

    override fun onSupportNavigateUp(): Boolean {
        if (!super.onSupportNavigateUp()) finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.profile_config_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem) = child.onOptionsItemSelected(item)

    val pluginHelp = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        (resultCode, data) ->
        if (resultCode == Activity.RESULT_OK) AlertDialog.Builder(this)
                .setTitle("?")
                .setMessage(data?.getCharSequenceExtra(PluginContract.EXTRA_HELP_MESSAGE))
                .show()
    }
}
