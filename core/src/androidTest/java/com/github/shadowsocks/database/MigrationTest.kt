

package com.github.shadowsocks.database

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MigrationTest {
    companion object {
        private const val TEST_DB = "migration-test"
    }

    @get:Rule
    val privateDatabase = MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
            PrivateDatabase::class.java.canonicalName, FrameworkSQLiteOpenHelperFactory())

    @Test
    @Throws(IOException::class)
    fun migrate27() {
        val db = privateDatabase.createDatabase(TEST_DB, 26)
        db.close()
        privateDatabase.runMigrationsAndValidate(TEST_DB, 27, true, PrivateDatabase.Migration27)
    }
    @Test
    @Throws(IOException::class)
    fun migrate28() {
        val db = privateDatabase.createDatabase(TEST_DB, 27)
        db.close()
        privateDatabase.runMigrationsAndValidate(TEST_DB, 28, true, PrivateDatabase.Migration28)
    }
}
