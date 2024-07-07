

package com.github.shadowsocks.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.shadowsocks.Core
import com.github.shadowsocks.database.migration.RecreateSchemaMigration
import com.github.shadowsocks.utils.Key
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [KeyValuePair::class], version = 3)
abstract class PublicDatabase : RoomDatabase() {
    companion object {
        private val instance by lazy {
            Room.databaseBuilder(Core.deviceStorage, PublicDatabase::class.java, Key.DB_PUBLIC).apply {
                addMigrations(
                        Migration3
                )
                allowMainThreadQueries()
                enableMultiInstanceInvalidation()
                fallbackToDestructiveMigration()
                setQueryExecutor { GlobalScope.launch { it.run() } }
            }.build()
        }

        val kvPairDao get() = instance.keyValuePairDao()
    }
    abstract fun keyValuePairDao(): KeyValuePair.Dao

    internal object Migration3 : RecreateSchemaMigration(2, 3, "KeyValuePair",
            "(`key` TEXT NOT NULL, `valueType` INTEGER NOT NULL, `value` BLOB NOT NULL, PRIMARY KEY(`key`))",
            "`key`, `valueType`, `value`")
}
