

package com.localzet.shadowsocks.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

open class RecreateSchemaMigration(oldVersion: Int, newVersion: Int, private val table: String,
                                   private val schema: String, private val keys: String) :
        Migration(oldVersion, newVersion) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE `tmp` $schema")
        database.execSQL("INSERT INTO `tmp` ($keys) SELECT $keys FROM `$table`")
        database.execSQL("DROP TABLE `$table`")
        database.execSQL("ALTER TABLE `tmp` RENAME TO `$table`")
    }
}
