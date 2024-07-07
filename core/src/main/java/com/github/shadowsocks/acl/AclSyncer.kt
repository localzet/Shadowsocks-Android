

package com.github.shadowsocks.acl

import android.content.Context
import android.os.Build
import androidx.work.*
import com.github.shadowsocks.Core
import com.github.shadowsocks.Core.app
import com.github.shadowsocks.utils.useCancellable
import timber.log.Timber
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit

class AclSyncer(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {
    companion object {
        private const val KEY_ROUTE = "route"

        fun schedule(route: String) {
            if (Build.VERSION.SDK_INT >= 24 && !Core.user.isUserUnlocked) return    // work does not support this
            WorkManager.getInstance(app).enqueueUniqueWork(
                    route, ExistingWorkPolicy.REPLACE, OneTimeWorkRequestBuilder<AclSyncer>().run {
                setInputData(Data.Builder().putString(KEY_ROUTE, route).build())
                setConstraints(Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.UNMETERED)
                        .setRequiresCharging(true)
                        .build())
                setInitialDelay(10, TimeUnit.SECONDS)
                build()
            })
        }
    }

    override suspend fun doWork(): Result = try {
        val route = inputData.getString(KEY_ROUTE)!!
        val connection = URL("https://shadowsocks.org/acl/android/v1/$route.acl").openConnection() as HttpURLConnection
        val acl = connection.useCancellable { inputStream.bufferedReader().use { it.readText() } }
        Acl.getFile(route).printWriter().use { it.write(acl) }
        Result.success()
    } catch (e: IOException) {
        Timber.d(e)
        if (runAttemptCount > 5) Result.failure() else Result.retry()
    }
}
