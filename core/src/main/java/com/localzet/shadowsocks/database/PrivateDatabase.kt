

package com.localzet.shadowsocks.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.localzet.shadowsocks.Core.app
import com.localzet.shadowsocks.database.migration.RecreateSchemaMigration
import com.localzet.shadowsocks.utils.Key
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Profile::class, KeyValuePair::class], version = 29)
@TypeConverters(Profile.SubscriptionStatus::class)
abstract class PrivateDatabase : RoomDatabase() {
    companion object {
        private val instance by lazy {
            Room.databaseBuilder(app, PrivateDatabase::class.java, Key.DB_PROFILE).apply {
                addMigrations(
                        Migration26,
                        Migration27,
                        Migration28,
                        Migration29
                )
                allowMainThreadQueries()
                enableMultiInstanceInvalidation()
                fallbackToDestructiveMigration()
                setQueryExecutor { GlobalScope.launch { it.run() } }
            }.build()
        }

        val profileDao get() = instance.profileDao()
        val kvPairDao get() = instance.keyValuePairDao()
    }
    abstract fun profileDao(): Profile.Dao
    abstract fun keyValuePairDao(): KeyValuePair.Dao

    object Migration26 : RecreateSchemaMigration(25, 26, "Profile",
            "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `host` TEXT NOT NULL, `remotePort` INTEGER NOT NULL, `password` TEXT NOT NULL, `method` TEXT NOT NULL, `route` TEXT NOT NULL, `remoteDns` TEXT NOT NULL, `proxyApps` INTEGER NOT NULL, `bypass` INTEGER NOT NULL, `udpdns` INTEGER NOT NULL, `ipv6` INTEGER NOT NULL, `individual` TEXT NOT NULL, `tx` INTEGER NOT NULL, `rx` INTEGER NOT NULL, `userOrder` INTEGER NOT NULL, `plugin` TEXT)",
            "`id`, `name`, `host`, `remotePort`, `password`, `method`, `route`, `remoteDns`, `proxyApps`, `bypass`, `udpdns`, `ipv6`, `individual`, `tx`, `rx`, `userOrder`, `plugin`") {
        override fun migrate(database: SupportSQLiteDatabase) {
            super.migrate(database)
            PublicDatabase.Migration3.migrate(database)
        }
    }
    object Migration27 : Migration(26, 27) {
        override fun migrate(database: SupportSQLiteDatabase) =
                database.execSQL("ALTER TABLE `Profile` ADD COLUMN `udpFallback` INTEGER")
    }
    object Migration28 : Migration(27, 28) {
        override fun migrate(database: SupportSQLiteDatabase) =
                database.execSQL("ALTER TABLE `Profile` ADD COLUMN `metered` INTEGER NOT NULL DEFAULT 0")
    }
    object Migration29 : Migration(28, 29) {
        override fun migrate(database: SupportSQLiteDatabase) =
                database.execSQL("ALTER TABLE `Profile` ADD COLUMN `subscription` INTEGER NOT NULL DEFAULT " +
                        Profile.SubscriptionStatus.UserConfigured.persistedValue)
    }
}
